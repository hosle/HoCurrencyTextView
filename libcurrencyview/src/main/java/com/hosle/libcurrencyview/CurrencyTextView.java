package com.hosle.libcurrencyview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StrikethroughSpan;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Created by tanjiahao on 17/7/28.
 *
 */

public class CurrencyTextView extends TextView implements ICurrencyFeature {

    private String currencySymbol;
    private CharSequence textContent;
    private CharSequence prefixText;
    private CharSequence suffixText;

    private boolean strikeThrough;
    private boolean nullToZero;
    private float symbolSize;
    private float decimalSize;
    private float prefixSuffixSize;

    public CurrencyTextView(Context context) {
        super(context);
    }

    public CurrencyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttrs(context, attrs);
    }

    public CurrencyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setText(textContent);
    }

    @Override
    public void setText(CharSequence text, CharSequence prefixText, CharSequence suffixText) {
        this.prefixText = prefixText;
        this.suffixText = suffixText;
        setText(text);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        textContent = text;
        super.setText(formatCurrencyString(text), type);
    }

    @Override
    public void setPrefixText(CharSequence prefixText) {
        this.prefixText = prefixText;
        setText(textContent);
    }

    @Override
    public void setSuffixText(CharSequence suffixText) {
        this.suffixText = suffixText;
        setText(textContent);
    }

    @Override
    public String getValueString() {
        return textContent.toString();
    }

    @Override
    public void clearPrefixText() {
        setPrefixText(null);
    }

    @Override
    public void clearSuffixText() {
        setSuffixText(null);
    }

    @Override
    public void setSymbol(CharSequence symbolString) {
        this.currencySymbol = String.valueOf(symbolString);
    }

    private void getAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CurrencyTextView);
        strikeThrough = ta.getBoolean(R.styleable.CurrencyTextView_strikeThrough, false);
        nullToZero = ta.getBoolean(R.styleable.CurrencyTextView_nullToZero, true);
        symbolSize = ta.getDimensionPixelSize(R.styleable.CurrencyTextView_currencySymbolSize, (int) getTextSize());
        decimalSize = ta.getDimensionPixelSize(R.styleable.CurrencyTextView_decimalTextSize, (int) getTextSize());
        prefixSuffixSize = ta.getDimensionPixelSize(R.styleable.CurrencyTextView_prefixSuffixTextSize, (int) getTextSize());
        String customSymbol = ta.getString(R.styleable.CurrencyTextView_currencySymbol);
        currencySymbol = TextUtils.isEmpty(customSymbol) ? "¥" : customSymbol;
        ta.recycle();
    }

    private Spanny formatCurrencyString(CharSequence oriPrice) {
        Spanny oriPriceChars;
        Spanny spanny = new Spanny()
                .append(prefixText,new AbsoluteSizeSpan((int) prefixSuffixSize, false));

        if ("".equals(oriPrice) || null == oriPrice) {
            if (nullToZero) {
                oriPrice = "0";
            } else {
                spanny.append(suffixText, new AbsoluteSizeSpan((int) prefixSuffixSize, false));
                return spanny;
            }
        }

        oriPriceChars = setUpNumberStyle(oriPrice);

        spanny.append(currencySymbol, new AbsoluteSizeSpan((int) symbolSize, false))
                .append(oriPriceChars,
                        strikeThrough ? new StrikethroughSpan() : null)
                .append(suffixText, new AbsoluteSizeSpan((int) prefixSuffixSize, false));
        return spanny;
    }

    private Spanny setUpNumberStyle(CharSequence oriPrice){
        Spanny oriPriceChars;
        if (decimalSize != getTextSize())
            oriPriceChars = setUpLargeSmallCurrency(oriPrice);
        else
            oriPriceChars = new Spanny(oriPrice, new AbsoluteSizeSpan((int) getTextSize(), false));

        return oriPriceChars;
    }

    private Spanny setUpLargeSmallCurrency(CharSequence amount) {
        Spanny result = new Spanny();
        try {
            double origin = Double.parseDouble(amount.toString());
            double integer = Math.floor(origin);
            double decimal = origin * 100 - integer * 100;
            result.append(String.valueOf((int) integer))
                    .append(".")
                    .setSpan(new AbsoluteSizeSpan((int) getTextSize(), false), 0, result.length());
            result.append(String.valueOf((int) decimal), new AbsoluteSizeSpan((int) decimalSize, false));

        } catch (NumberFormatException e) {
            e.printStackTrace();
            result.append(amount, new AbsoluteSizeSpan((int) getTextSize(), false));
        }
        return result;
    }

}

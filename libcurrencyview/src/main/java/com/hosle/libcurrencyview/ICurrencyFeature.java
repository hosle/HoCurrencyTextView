package com.hosle.libcurrencyview;

/**
 * Created by tanjiahao on 17/8/1.
 */

public interface ICurrencyFeature {
    void setText(CharSequence text, CharSequence prefixText, CharSequence suffixText);
    void setPrefixText(CharSequence prefixText);
    void setSuffixText(CharSequence suffixText);
    void clearPrefixText();
    void clearSuffixText();
    String getValueString();

    void setSymbol(CharSequence symbolString);
}

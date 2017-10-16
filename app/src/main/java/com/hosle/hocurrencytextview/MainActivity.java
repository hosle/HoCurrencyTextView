package com.hosle.hocurrencytextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hosle.libcurrencyview.CurrencyTextView;

import java.util.Currency;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private CurrencyTextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1 = (CurrencyTextView) findViewById(R.id.textview1);
//        textView1.setPrefixText("应付款 ");
//        textView1.setSuffixText(" 可用");
        textView1.setSymbol(Currency.getInstance(Locale.getDefault()).getSymbol());
        textView1.setText(textView1.getValueString(),"可用"," 应付款");
//        textView1.setText("1346811.2");
//        Toast toast = Toast.makeText(this,textView1.getValueString(),Toast.LENGTH_SHORT);
//        toast.show();

    }
}

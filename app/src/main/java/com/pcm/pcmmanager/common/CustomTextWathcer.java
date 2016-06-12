package com.pcm.pcmmanager.common;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;

/**
 * Created by LG on 2016-05-31.
 */
public class CustomTextWathcer implements TextWatcher {

    @SuppressWarnings("unused")
    private EditText mEditText;
    String strAmount = ""; //임시 저장(콤마)

    public CustomTextWathcer(EditText e) {
        mEditText = e;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!s.toString().equals(strAmount)) {
            strAmount = marketStringComma(s.toString().replace(",", ""));
            mEditText.setText(strAmount);
            Editable e = mEditText.getText();
            Selection.setSelection(e, strAmount.length());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    protected String marketStringComma(String str) {
        if (str.length() == 0)
            return "";
        long value = Long.parseLong(str);
        DecimalFormat format = new DecimalFormat("###,###");
        return format.format(value);
    }
}

package com.burguer.zap.burguer.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.NumberFormat;

/**
 * Created by LucasOrso on 11/18/17.
 */

public class MonetaryWatcher implements TextWatcher {

    private final EditText mEditText;
    private boolean mIsUpdating = false;
    private NumberFormat mNumberFormat = NumberFormat.getCurrencyInstance();

    public MonetaryWatcher(EditText aEditText) {
        mEditText = aEditText;
    }

    @Override
    public void beforeTextChanged(CharSequence aCharSequence, int aI, int aI1, int aI2) {/*Nothing*/}

    @Override
    public void onTextChanged(CharSequence aCharSequence, int aI, int aI1, int aI2) {
        if (mIsUpdating) {
            mIsUpdating = false;
            return;
        }
        mIsUpdating = true;
        String lCurrentText = aCharSequence.toString();
        lCurrentText = lCurrentText.replaceAll("[^\\d]", "");
        try {
            lCurrentText = mNumberFormat.format(Double.parseDouble(lCurrentText) / 100);
            mEditText.setText(lCurrentText);
            mEditText.setSelection(mEditText.getText().length());
        } catch (NumberFormatException aE) {
            aE.printStackTrace();
        }
    }
    

    @Override
    public void afterTextChanged(Editable aEditable) {/*Nothing*/}
}

package com.burguer.zap.burguer.activities.base;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by LucasOrso on 10/6/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public static void hideSoftKeyboard(Activity aActivity) {
        try {
            InputMethodManager lInputMethodManager = (InputMethodManager) aActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            lInputMethodManager.hideSoftInputFromWindow(aActivity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception aE) {
            aE.printStackTrace();
        }
    }
}

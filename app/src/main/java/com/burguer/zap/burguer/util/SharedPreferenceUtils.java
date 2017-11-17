package com.burguer.zap.burguer.util;

/**
 * Created by LucasOrso on 11/16/17.
 */

import android.content.Context;
import android.content.SharedPreferences;

import com.burguer.zap.burguer.BuildConfig;

public class SharedPreferenceUtils {

    public static SharedPreferenceUtils sSharedPreferenceUtils;
    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mSharedPreferencesEditor;

    private SharedPreferenceUtils(Context context) {
        mContext = context;
        mSharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        mSharedPreferencesEditor = mSharedPreferences.edit();
    }

    public static synchronized SharedPreferenceUtils getInstance(Context context) {

        if (sSharedPreferenceUtils == null) {
            sSharedPreferenceUtils = new SharedPreferenceUtils(context);
        }
        return sSharedPreferenceUtils;
    }

    public void setValue(String key, String value) {
        mSharedPreferencesEditor.putString(key, value);
        mSharedPreferencesEditor.apply();
    }

    public void setValue(String key, int value) {
        mSharedPreferencesEditor.putInt(key, value);
        mSharedPreferencesEditor.apply();
    }

    public void setValue(String key, double value) {
        setValue(key, Double.toString(value));
    }

    public void setValue(String key, long value) {
        mSharedPreferencesEditor.putLong(key, value);
        mSharedPreferencesEditor.apply();
    }
    public void setValue(String key, boolean value) {
        mSharedPreferencesEditor.putBoolean(key, value);
        mSharedPreferencesEditor.apply();
    }

    public String getStringValue(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    public int getIntValue(String key, int defaultValue) {
        return mSharedPreferences.getInt(key, defaultValue);
    }

    public long getLongValue(String key, long defaultValue) {
        return mSharedPreferences.getLong(key, defaultValue);
    }

    public boolean getBoolanValue(String keyFlag, boolean defaultValue) {
        return mSharedPreferences.getBoolean(keyFlag, defaultValue);
    }

    public void removeKey(String key) {
        if (mSharedPreferencesEditor != null) {
            mSharedPreferencesEditor.remove(key);
            mSharedPreferencesEditor.apply();
        }
    }

    public void clear() {
        mSharedPreferencesEditor.clear().commit();
    }
}
package com.kk.imageeditor.controllor;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.kk.imageeditor.Constants;
import com.kk.imageeditor.R;

import java.util.ArrayList;
import java.util.List;

public class MyPreference {
    private static MyPreference sMyPreference = null;

    public static MyPreference get(Context context) {
        if (sMyPreference == null) {
            synchronized (MyPreference.class) {
                sMyPreference = new MyPreference(context.getApplicationContext());
            }
        }
        return sMyPreference;
    }
    public final String KEY_SHORT_SET_NAME;
    public final String KEY_PATH_CACHE;
    public final String KEY_PATH_IMAGE;
    public final String KEY_PATH_STYLE;
    public final String KEY_PATH_TEMP;
    public final String KEY_STYLE_PATH;
    private SharedPreferences mSharedPreferences;

    private MyPreference(Context context) {
        KEY_SHORT_SET_NAME = context.getString(R.string.settings_key_short_set_name);
        KEY_PATH_CACHE = context.getString(R.string.settings_key_path_cache);
        KEY_PATH_IMAGE = context.getString(R.string.settings_key_path_image);
        KEY_PATH_STYLE = context.getString(R.string.settings_key_path_style);
        KEY_PATH_TEMP = context.getString(R.string.settings_key_path_temp);
        KEY_STYLE_PATH = context.getString(R.string.settings_key_style_path);
        mSharedPreferences = context.getSharedPreferences(Constants.SETTINGS_NAME, Context.MODE_MULTI_PROCESS | Context.MODE_PRIVATE);
        mCurStyle = mSharedPreferences.getString(KEY_STYLE_PATH, null);
    }

    private String mCurStyle;

    public void setCurStyle(String curStyle) {
        if (!TextUtils.equals(mCurStyle, curStyle)) {
            mCurStyle = curStyle;
            mSharedPreferences.edit().putString(KEY_STYLE_PATH, curStyle).commit();
        }
    }

    public boolean showFullSetName() {
        return mSharedPreferences.getBoolean(KEY_SHORT_SET_NAME, true);
    }
    public void setFullSetName(boolean fullname) {
        mSharedPreferences.edit().putBoolean(KEY_SHORT_SET_NAME, fullname).commit();
    }
    public String getCurStyle() {
        return mCurStyle;
    }

    public SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }
}

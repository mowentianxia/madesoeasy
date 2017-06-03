package com.kk.imageeditor.controllor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.text.TextUtils;

import com.kk.imageeditor.Constants;
import com.kk.imageeditor.R;

import java.io.File;

import static com.kk.imageeditor.Constants.DEFAULT_NAME;
import static com.kk.imageeditor.Constants.PREF_WORKPATH;

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

    public final String KEY_LONG_SET_NAME;
    public final String KEY_PATH_CACHE;
    public final String KEY_PATH_IMAGE;
    public final String KEY_PATH_STYLE;
    public final String KEY_PATH_TEMP;
    public final String KEY_STYLE_PATH;
    public final String KEY_IMGE_TYPE;
    public final String KEY_HARDWARE;
    public final String KEY_CUT_SCALE;
    private SharedPreferences mSharedPreferences;

    //默认文件夹路径
    private String mWorkPath;
    private String mCachePath;
    private String mImagePath;
    private String mStylePath;
    private String mTempPath;
    private String mImageType;

    private String defImageType;

    private MyPreference(Context context) {
        KEY_LONG_SET_NAME = context.getString(R.string.settings_key_long_set_name);
        KEY_PATH_CACHE = context.getString(R.string.settings_key_path_cache);
        KEY_PATH_IMAGE = context.getString(R.string.settings_key_path_image);
        KEY_PATH_STYLE = context.getString(R.string.settings_key_path_style);
        KEY_PATH_TEMP = context.getString(R.string.settings_key_path_temp);
        KEY_STYLE_PATH = context.getString(R.string.settings_key_style_path);
        KEY_IMGE_TYPE = context.getString(R.string.settings_key_image_type);
        KEY_HARDWARE = context.getString(R.string.settings_hardware);
        KEY_CUT_SCALE = context.getString(R.string.settings_cut_image_scale);
        mSharedPreferences = context.getSharedPreferences(Constants.SETTINGS_NAME, Context.MODE_MULTI_PROCESS | Context.MODE_PRIVATE);
        mCurStyle = mSharedPreferences.getString(KEY_STYLE_PATH, null);
        defImageType = context.getString(R.string.settings_default_image_type);
        String defPath = null;
        try {
            defPath = new File(Environment.getExternalStorageDirectory(), DEFAULT_NAME).getAbsolutePath();
        } catch (Exception e) {
            defPath = new File(context.getFilesDir(), DEFAULT_NAME).getAbsolutePath();
        }
        mWorkPath = mSharedPreferences.getString(PREF_WORKPATH, defPath);
        mCachePath = mSharedPreferences.getString(KEY_PATH_CACHE, new File(defPath, "cache").getAbsolutePath());
        mImagePath = mSharedPreferences.getString(KEY_PATH_IMAGE, new File(defPath, "images").getAbsolutePath());
        mStylePath = mSharedPreferences.getString(KEY_PATH_STYLE, new File(defPath, "style").getAbsolutePath());
        mTempPath = mSharedPreferences.getString(KEY_PATH_TEMP, new File(defPath, "temp").getAbsolutePath());
        mImageType = mSharedPreferences.getString(KEY_IMGE_TYPE, defImageType);
    }

    private String mCurStyle;

    public void setCurStyle(String curStyle) {
        if (!TextUtils.equals(mCurStyle, curStyle)) {
            mCurStyle = curStyle;
            mSharedPreferences.edit().putString(KEY_STYLE_PATH, curStyle).apply();
        }
    }

    public boolean showFullSetName() {
        return mSharedPreferences.getBoolean(KEY_LONG_SET_NAME, false);
    }

    public void setFullSetName(boolean fullname) {
        mSharedPreferences.edit().putBoolean(KEY_LONG_SET_NAME, fullname).apply();
    }

    public String getCurStyle() {
        return mCurStyle;
    }

    public SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    public String getWorkPath() {
        return mWorkPath;
    }

    public String getStylePath() {
        return mStylePath;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public String getImageType() {
        return mImageType;
    }

    public void setImageType(String imageType) {
        if(TextUtils.equals(imageType, mImageType)){
            return;
        }
        mImageType = imageType;
        mSharedPreferences.edit().putString(KEY_IMGE_TYPE, imageType).apply();
    }

    public String getCachePath() {
        return mCachePath;
    }

    public String getTempPath() {
        return mTempPath;
    }

    public boolean isHardware(){
        return mSharedPreferences.getBoolean(KEY_HARDWARE, false);
    }

    /***
     * 按照当前比例裁剪
     * @return
     */
    public boolean isCutUseScale(){
        return mSharedPreferences.getBoolean(KEY_CUT_SCALE, true);
    }

    public void setFolder(DirType type, String path) {
        switch (type) {
            case Cache:
                if (!TextUtils.equals(path, mCachePath)) {
                    mCachePath = path;
                    mSharedPreferences.edit().putString(KEY_PATH_CACHE, path).apply();
                }
                break;
            case Temp:
                if (!TextUtils.equals(path, mTempPath)) {
                    mTempPath = path;
                    mSharedPreferences.edit().putString(KEY_PATH_STYLE, path).apply();
                }
                break;
            case Styles:
                if (!TextUtils.equals(path, mStylePath)) {
                    mStylePath = path;
                    mSharedPreferences.edit().putString(KEY_PATH_TEMP, path).apply();
                }
                break;
            case Images:
                if (!TextUtils.equals(path, mImagePath)) {
                    mImagePath = path;
                    mSharedPreferences.edit().putString(KEY_PATH_IMAGE, path).apply();
                }
                break;
            case Work:
                if (!TextUtils.equals(path, mWorkPath)) {
                    mWorkPath = path;
                    mSharedPreferences.edit().putString(PREF_WORKPATH, path).apply();
                }
                break;
        }
    }

    public enum DirType {
        Cache,
        Temp,
        Styles,
        Images,
        Work
    }
}

package com.kk.imageeditor.controllor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.text.TextUtils;

import com.kk.imageeditor.utils.FileUtil;

import java.io.File;

import static com.kk.imageeditor.Constants.DEFAULT_NAME;
import static com.kk.imageeditor.Constants.PREF_WORKPATH;

public class PathConrollor extends BaseControllor {

    private String workPath;
    private String cachePath;
    private String tempPath;
    private String stylePath;
    private String setPath;
    private String imagePath;
    private String mCurPath;

    PathConrollor(Context context, SharedPreferences sharedPreferences) {
        super(context, sharedPreferences);
        String defPath = null;
        try {
            defPath = new File(Environment.getExternalStorageDirectory(), DEFAULT_NAME).getAbsolutePath();
        } catch (Exception e) {
            defPath = new File(context.getFilesDir(), DEFAULT_NAME).getAbsolutePath();
        }
        workPath = sharedPreferences.getString(PREF_WORKPATH, defPath);
        initPath(workPath);
    }

    public String getWorkPath() {
        return workPath;
    }

    public String getCachePath() {
        return cachePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getSetPath() {
        return setPath;
    }

    public String getTempPath() {
        return tempPath;
    }

    public String getCurPath() {
        return mCurPath;
    }

    public void setCurPath(String mCutPath) {
        this.mCurPath = mCutPath;
    }

    public String getStylePath() {
        return stylePath;
    }

    private void initPath(String workPath) {
        if (!TextUtils.equals(this.workPath, workPath)) {
            sharedPreferences.edit().putString(PREF_WORKPATH, workPath).apply();
        }
        this.workPath = workPath;

        cachePath = new File(workPath, "cache").getAbsolutePath();
        FileUtil.createNoMedia(cachePath);
        tempPath = new File(workPath, "temp").getAbsolutePath();
        FileUtil.createNoMedia(tempPath);
        imagePath = new File(workPath, "images").getAbsolutePath();
        setPath = new File(workPath, "saves").getAbsolutePath();
        stylePath = new File(workPath, "styles").getAbsolutePath();
        if (TextUtils.isEmpty(mCurPath)) {
            mCurPath = workPath;
        }
    }
}

package com.kk.imageeditor.controllor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

public class PathConrollor extends BaseControllor {
    private static final String NAME = "MadeSoEasy";
    private static final String WORKPATH = "workpath";
    private String workPath;
    private String cachePath;
    private String stylePath;
    private String setPath;
    private String imagePath;
    private String mCurPath;

    PathConrollor(Context context, SharedPreferences sharedPreferences) {
        super(context, sharedPreferences);
        String defPath = null;
        try {
            defPath = new File(Environment.getExternalStorageDirectory(), NAME).getAbsolutePath();
        } catch (Exception e) {
            defPath = new File(context.getFilesDir(), NAME).getAbsolutePath();
        }
        workPath = sharedPreferences.getString(WORKPATH, defPath);
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
        if(!TextUtils.equals(this.workPath, workPath)){
            sharedPreferences.edit().putString(WORKPATH, workPath).apply();
        }
        this.workPath = workPath;
        cachePath = new File(workPath, "cache").getAbsolutePath();
        imagePath = new File(workPath, "images").getAbsolutePath();
        setPath = new File(workPath, "saves").getAbsolutePath();
        stylePath = new File(workPath, "styles").getAbsolutePath();
        if (TextUtils.isEmpty(mCurPath)) {
            mCurPath = workPath;
        }
    }
}

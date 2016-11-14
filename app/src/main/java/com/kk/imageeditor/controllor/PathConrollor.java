package com.kk.imageeditor.controllor;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class PathConrollor extends BaseControllor {
    private static final String NAME = "MadeSoEasy";
    private String workPath;
    private String cachePath;
    private String stylePath;
    private String setPath;
    private String imagePath;

    PathConrollor(Context context) {
        super(context);
        initPath();
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

    public String getStylePath() {
        return stylePath;
    }

    private void initPath() {
        try {
            workPath = new File(Environment.getExternalStorageDirectory(), NAME).getAbsolutePath();
        } catch (Exception e) {
            workPath = new File(context.getFilesDir(), NAME).getAbsolutePath();
        }
        cachePath = new File(workPath, "cache").getAbsolutePath();
        imagePath = new File(workPath, "images").getAbsolutePath();
        setPath = new File(workPath, "saves").getAbsolutePath();
        stylePath = new File(workPath, "styles").getAbsolutePath();
    }
}

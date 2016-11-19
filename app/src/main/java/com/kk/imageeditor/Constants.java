package com.kk.imageeditor;


public interface Constants {
    String ASSET_STYLE = "style";
    float SCALE_SETP = 0.25f;
    boolean DEBUG = BuildConfig.DEBUG;
    int REQUEST_PERMISSIONS = 0x1000 + 1;
    int REQUEST_CHOOSE_IMG = 0x1000 + 0x20;
    int REQUEST_CUT_IMG = 0x1000 + 0x10;
    int REQUEST_CAPTURE = 0x1000 + 0x30;
    int REQUEST_STYLE = 0x1000 + 2;
    int RESULT_STYLE = 0x1000 + 4;
    String PREVIEW_NAME = "preview.png";
    String[] STYLE_EX = {".xml", ".zip", ".style"};
    String[] IMAGE_EX = {".jpg", ".png"};
    String SET_EX1 = ".dex-set";
    String SET_EX2 = ".msoe-set";
    String ZIP_SET = "set";
    String[] SET_EX = new String[]{SET_EX1, SET_EX2};
    String EXTRA_FILE = "filepath";

    //path
    String DEFAULT_NAME = "MadeSoEasy";
    String PREF_WORKPATH = "workpath";
}

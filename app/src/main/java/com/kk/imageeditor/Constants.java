package com.kk.imageeditor;


public interface Constants {
    String FILE_AUTH = BuildConfig.APPLICATION_ID + ".fileProvider";
    String SETTINGS_NAME = "app_settings";
    String DEFAULT_STYLE = BuildConfig.DEFAULT_STYLE;
    String ASSET_STYLE = "style";
    String STYLE_XMl = "style.xml";
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
    String DEFAULT_NAME = "MadeSoEasy2";
    String PREF_WORKPATH = "workpath";
    String PREF_LAST_SET = "last_set";

    String SETTINGS_CATEGORY = "extra_settings_category";
    int SETTINGS_CATEGORY_STYLE = 1;
}

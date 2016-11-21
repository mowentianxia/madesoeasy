package com.kk.imageeditor.controllor;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.prefs.Preferences;

public class ControllorManager {
    private static final ControllorManager CONTROLLOR_MANAGER = new ControllorManager();
    private Application context;
    private PathConrollor pathConrollor;
    private StyleControllor styleControllor;
    private SharedPreferences sharedPreferences;
    private static final String PREF_FILE="drawer.config";

    private ControllorManager() {

    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void attach(Application application) {
        this.context = application;
        sharedPreferences = application.getSharedPreferences(PREF_FILE,Context.MODE_PRIVATE);
        pathConrollor = new PathConrollor(application, sharedPreferences);
        styleControllor = new StyleControllor(application, sharedPreferences);
    }

    public static ControllorManager get() {
        return CONTROLLOR_MANAGER;
    }

    public Application getContext() {
        return context;
    }

    public PathConrollor getPathConrollor() {
        return pathConrollor;
    }

    public StyleControllor getStyleControllor() {
        return styleControllor;
    }
}

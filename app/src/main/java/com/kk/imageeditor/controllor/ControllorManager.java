package com.kk.imageeditor.controllor;

import android.app.Application;
import android.content.Context;

public class ControllorManager {
    public static final ControllorManager CONTROLLOR_MANAGER = new ControllorManager();
    private Application context;
    private PathConrollor pathConrollor;
    private StyleControllor styleControllor;

    private ControllorManager() {

    }

    public void attach(Application application) {
        this.context = application;
        pathConrollor = new PathConrollor(application);
        styleControllor = new StyleControllor(application);
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

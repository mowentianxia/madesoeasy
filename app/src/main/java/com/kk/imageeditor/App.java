package com.kk.imageeditor;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.kk.common.VDeferredManager;
import com.kk.imageeditor.controllor.ControllorManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        VDeferredManager.init(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        ControllorManager.get().attach(this);
    }
}

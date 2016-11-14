package com.kk.imageeditor;

import android.app.Application;

import com.kk.imageeditor.controllor.ControllorManager;

public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        ControllorManager.get().attach(this);
    }
}

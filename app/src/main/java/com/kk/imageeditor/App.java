package com.kk.imageeditor;

import android.app.Application;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatDelegate;

import com.kk.imageeditor.controllor.ControllorManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        ControllorManager.get().attach(this);
        WifiManager wifiManager;
    }
}

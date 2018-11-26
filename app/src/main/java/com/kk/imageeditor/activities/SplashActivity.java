package com.kk.imageeditor.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import com.kk.common.base.BaseActivity;
import com.kk.imageeditor.R;

public class SplashActivity extends BaseActivity {

    private Handler handler = new Handler();

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected String[] getRequestPermissions() {
        return new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.INSTALL_SHORTCUT,
        };
    }

    @Override
    protected void doOnCreate(@Nullable Bundle savedInstanceState) {
        super.doOnCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler = new Handler();
    }

    @Override
    protected void initData() {
        super.initData();
        handler.postDelayed(() -> {
            MainActivity.start(this);
            finish();
        }, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}

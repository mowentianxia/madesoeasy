package com.kk.imageeditor.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.kk.common.base.BaseActivity;
import com.kk.imageeditor.Constants;
import com.kk.imageeditor.R;
import com.kk.imageeditor.fragments.SettingFragment;

public class SettingsActivity extends BaseActivity {
    private SettingFragment mSettingFragment;
    @Override
    protected void doOnCreate(@Nullable Bundle savedInstanceState) {
        super.doOnCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        enableBackHome();
        int type = 0;
        if(getIntent().hasExtra(Constants.SETTINGS_CATEGORY)){
            type = getIntent().getIntExtra(Constants.SETTINGS_CATEGORY, 0);
        }
        mSettingFragment = new SettingFragment().initCategory(type);
        getFragmentManager().beginTransaction().replace(R.id.fragment, mSettingFragment).commit();
    }
}

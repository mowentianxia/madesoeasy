package com.kk.imageeditor.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kk.imageeditor.Constants;
import com.kk.imageeditor.fragments.SettingFragment;

public class SettingsActivity extends BaseActivity {
    private SettingFragment mSettingFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableBackHome();
        int type = 0;
        if(getIntent().hasExtra(Constants.SETTINGS_CATEGORY)){
            type = getIntent().getIntExtra(Constants.SETTINGS_CATEGORY, 0);
        }
        mSettingFragment = new SettingFragment().initCategory(type);
        getFragmentManager().beginTransaction().replace(android.R.id.content, mSettingFragment).commit();
    }
}

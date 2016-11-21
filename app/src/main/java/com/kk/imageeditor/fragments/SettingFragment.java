package com.kk.imageeditor.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.text.TextUtils;

import com.kk.imageeditor.Constants;
import com.kk.imageeditor.R;
import com.kk.imageeditor.activities.StyleListActivity;

public class SettingFragment extends BasePreferenceFragment {
    private int mInitSettings;
    private String KEY_ABOUT;

    public SettingFragment() {

    }

    public SettingFragment initCategory(int settings) {
        mInitSettings = settings;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        addPreferencesFromResource(R.xml.pref_imageeditor);
        KEY_ABOUT = getString(R.string.settings_about);
        bindPreferenceSummaryToValue(findPreference(mMyPreference.KEY_SHORT_SET_NAME), ValueType.Boolean);
        bindPreferenceSummaryToValue(findPreference(mMyPreference.KEY_PATH_CACHE), ValueType.String);
        bindPreferenceSummaryToValue(findPreference(mMyPreference.KEY_PATH_IMAGE), ValueType.String);
        bindPreferenceSummaryToValue(findPreference(mMyPreference.KEY_PATH_STYLE), ValueType.String);
        bindPreferenceSummaryToValue(findPreference(mMyPreference.KEY_PATH_TEMP), ValueType.String);
        bindPreferenceSummaryToValue(findPreference(mMyPreference.KEY_STYLE_PATH), ValueType.String);
        bindPreferenceSummaryToValue(findPreference(KEY_ABOUT), ValueType.String);
        if (mInitSettings != 0) {
            switch (mInitSettings) {
                case Constants.SETTINGS_CATEGORY_STYLE:
                    startActivityForResult(new Intent(getActivity(), StyleListActivity.class), Constants.REQUEST_STYLE);
                    break;
            }
        }
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        if (mMyPreference.KEY_SHORT_SET_NAME.equals(key)) {
            if (preference instanceof CheckBoxPreference) {
                CheckBoxPreference checkBoxPreference = (CheckBoxPreference) preference;
                mMyPreference.setFullSetName(checkBoxPreference.isChecked());
            }
        } else if (mMyPreference.KEY_STYLE_PATH.equals(key)) {
            startActivityForResult(new Intent(getActivity(), StyleListActivity.class), Constants.REQUEST_STYLE);
        } else if (KEY_ABOUT.equals(key)) {
            showAboutInfo();
        }
        return false;
    }

    private void showAboutInfo() {
        try {
            PackageInfo packageInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            //关于信息
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getString(R.string.about));
            builder.setMessage(String.format(getString(R.string.about_info),
                    getString(R.string.app_name),
                    packageInfo.versionName));
            builder.setPositiveButton(android.R.string.ok, (v,s)->{
                v.dismiss();
            });
            Dialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_STYLE) {
            if (data != null) {
                String file = data.getStringExtra(Intent.EXTRA_TEXT);
                if (!TextUtils.isEmpty(file)) {
                    if (TextUtils.equals(mMyPreference.getCurStyle(), file)) {
                        return;
                    }
                    mMyPreference.setCurStyle(file);
                    findPreference(mMyPreference.KEY_STYLE_PATH).setSummary(file);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}


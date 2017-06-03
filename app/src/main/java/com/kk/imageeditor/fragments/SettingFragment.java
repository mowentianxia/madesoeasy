package com.kk.imageeditor.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.text.TextUtils;
import android.widget.Toast;

import com.kk.imageeditor.Constants;
import com.kk.imageeditor.R;
import com.kk.imageeditor.activities.StyleListActivity;
import com.kk.imageeditor.controllor.MyPreference;

import net.kk.dialog.FileDialog;
import net.kk.dialog.FileFilter2;

import java.io.File;

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
        bindPreferenceSummaryToValue(findPreference(mMyPreference.KEY_LONG_SET_NAME), ValueType.Boolean, mMyPreference.showFullSetName());

        bindPreferenceSummaryToValue(findPreference(mMyPreference.KEY_HARDWARE), ValueType.Boolean, mMyPreference.isHardware());
        bindPreferenceSummaryToValue(findPreference(mMyPreference.KEY_CUT_SCALE), ValueType.Boolean, mMyPreference.isCutUseScale());
        bindPreferenceSummaryToValue(findPreference(mMyPreference.KEY_STYLE_PATH), ValueType.String, "");
        bindPreferenceSummaryToValue(findPreference(mMyPreference.KEY_IMGE_TYPE), ValueType.String, mMyPreference.getImageType());
        bindPreferenceSummaryToValue(findPreference(KEY_ABOUT), ValueType.String, "");
        if (mInitSettings != 0) {
            switch (mInitSettings) {
                case Constants.SETTINGS_CATEGORY_STYLE:
                    startActivityForResult(new Intent(getActivity(), StyleListActivity.class), Constants.REQUEST_STYLE);
                    break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        if (mMyPreference.KEY_STYLE_PATH.equals(key)) {
            startActivityForResult(new Intent(getActivity(), StyleListActivity.class), Constants.REQUEST_STYLE);
        } else if (KEY_ABOUT.equals(key)) {
            showAboutInfo();
        } else if (mMyPreference.KEY_PATH_CACHE.equals(key)) {
            selectFolder(preference, MyPreference.DirType.Cache);
        } else if (mMyPreference.KEY_PATH_IMAGE.equals(key)) {
            selectFolder(preference, MyPreference.DirType.Images);
        } else if (mMyPreference.KEY_PATH_STYLE.equals(key)) {
            selectFolder(preference, MyPreference.DirType.Styles);
        } else if (mMyPreference.KEY_PATH_TEMP.equals(key)) {
            selectFolder(preference, MyPreference.DirType.Temp);
        }
        return false;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        super.onPreferenceChange(preference, value);
        if (preference instanceof CheckBoxPreference) {
            CheckBoxPreference checkBoxPreference = (CheckBoxPreference) preference;
            boolean checked = checkBoxPreference.isChecked();
            mSharedPreferences.edit().putBoolean(preference.getKey(), checked).apply();
            if (checked && mMyPreference.KEY_HARDWARE.equals(preference.getKey())) {
                Toast.makeText(getActivity(), R.string.tip_restart_app, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        boolean rs = super.onPreferenceChange(preference, value);
        if (mMyPreference.KEY_IMGE_TYPE.equals(preference.getKey())) {
            ListPreference listPreference = (ListPreference) preference;
            mMyPreference.setImageType(listPreference.getValue());
        }
        return rs;
    }

    private void selectFolder(Preference preference, MyPreference.DirType dirType) {
        FileDialog dialog = new FileDialog(getActivity(), FileDialog.Mode.ChooseDirectory);
        dialog.setTitle(R.string.open_set);
        dialog.setPath(new File(mMyPreference.getWorkPath()), new FileFilter2(false, Constants.SET_EX));
        dialog.setFileChooseListener((dlg, file) -> {
            if (file != null && file.isDirectory()) {
                preference.setSummary(file.getAbsolutePath());
                mMyPreference.setFolder(dirType, file.getAbsolutePath());
            }
        });
        dialog.show();
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
            builder.setPositiveButton(android.R.string.ok, (v, s) -> {
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


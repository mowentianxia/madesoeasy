package com.kk.imageeditor.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.text.TextUtils;
import android.util.Log;

import com.kk.imageeditor.Constants;
import com.kk.imageeditor.R;
import com.kk.imageeditor.activities.StyleListActivity;
import com.kk.imageeditor.controllor.MyPreference;
import com.kk.imageeditor.controllor.PathConrollor;
import com.kk.imageeditor.filebrowser.DialogFileFilter;
import com.kk.imageeditor.filebrowser.OpenFileDialog;
import com.kk.imageeditor.filebrowser.SaveFileDialog;

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

    protected Object getValue(String key, ValueType type) {
        if (mMyPreference.KEY_IMGE_TYPE.equals(key)) {
            String t = mMyPreference.getImageType();
            return t;
        } else {
            return super.getValue(key, type);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        addPreferencesFromResource(R.xml.pref_imageeditor);
        KEY_ABOUT = getString(R.string.settings_about);
        bindPreferenceSummaryToValue(findPreference(mMyPreference.KEY_SHORT_SET_NAME), ValueType.Boolean);

//        bindPreferenceSummaryToValue(findPreference(mMyPreference.KEY_PATH_CACHE), ValueType.String);
//        bindPreferenceSummaryToValue(findPreference(mMyPreference.KEY_PATH_IMAGE), ValueType.String);
//        bindPreferenceSummaryToValue(findPreference(mMyPreference.KEY_PATH_STYLE), ValueType.String);
//        bindPreferenceSummaryToValue(findPreference(mMyPreference.KEY_PATH_TEMP), ValueType.String);
//
        bindPreferenceSummaryToValue(findPreference(mMyPreference.KEY_STYLE_PATH), ValueType.String);
        bindPreferenceSummaryToValue(findPreference(mMyPreference.KEY_IMGE_TYPE), ValueType.String);
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
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        if (preference instanceof CheckBoxPreference) {
            CheckBoxPreference checkBoxPreference = (CheckBoxPreference) preference;
            mSharedPreferences.edit().putBoolean(key, checkBoxPreference.isChecked()).apply();
        } else if (mMyPreference.KEY_STYLE_PATH.equals(key)) {
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
        if(preference instanceof CheckBoxPreference){
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
        final OpenFileDialog fileDialog = new OpenFileDialog(getActivity());
        fileDialog.setCurPath(mMyPreference.getWorkPath());
        fileDialog.setDialogFileFilter(new DialogFileFilter(true, false));
        fileDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                getString(android.R.string.ok), (dialog, which) -> {
                    File file = fileDialog.getSelectFile();
                    if (file != null && file.isDirectory()) {
                        preference.setSummary(file.getAbsolutePath());
                        mMyPreference.setFolder(dirType, file.getAbsolutePath());
                    } else {
                        Log.e("msoe", "select ==null");
                    }
                });
        fileDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
                getString(android.R.string.cancel),
                (DialogInterface.OnClickListener) null);
        fileDialog.show();
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


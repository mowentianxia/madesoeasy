package com.kk.imageeditor.settings;

import android.os.Bundle;

import com.kk.imageeditor.R;

public class ImageEditorPreferenceFragment extends BasePreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_imageeditor);

        bindPreferenceSummaryToValue(findPreference("imageeditor_workpath"));
    }
}

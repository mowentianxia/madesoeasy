package com.kk.imageeditor.settings;

import android.os.Bundle;

import com.kk.imageeditor.R;

public class DataEditorPreferenceFragment extends BasePreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_dbeditor);

        bindPreferenceSummaryToValue(findPreference("dataeditor_scriptpath"));
        bindPreferenceSummaryToValue(findPreference("dataeditor_imagetpath"));
        bindPreferenceSummaryToValue(findPreference("dataeditor_databasepath"));
    }
}

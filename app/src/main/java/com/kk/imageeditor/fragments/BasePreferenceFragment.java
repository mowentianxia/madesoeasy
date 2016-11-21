package com.kk.imageeditor.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.view.MenuItem;

import com.kk.imageeditor.activities.SettingsActivity;
import com.kk.imageeditor.controllor.MyPreference;

abstract class BasePreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {
    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue;
            if (value instanceof String) {
                stringValue = (String) value;
            } else {
                stringValue = value.toString();
            }

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            } else if (preference instanceof RingtonePreference) {
                // For ringtone preferences, look up the correct display value
                // using RingtoneManager.
                if (TextUtils.isEmpty(stringValue)) {
                    // Empty values correspond to 'silent' (no ringtone).
                    preference.setSummary(null);

                } else {
                    Ringtone ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), Uri.parse(stringValue));

                    if (ringtone == null) {
                        // Clear the summary if there was a lookup error.
                        preference.setSummary(null);
                    } else {
                        // Set the summary to reflect the new ringtone display
                        // name.
                        String name = ringtone.getTitle(preference.getContext());
                        preference.setSummary(name);
                    }
                }

            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    protected <T> void bindPreferenceSummaryToValue(Preference preference, ValueType type) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);
        preference.setOnPreferenceClickListener(this);

        // Trigger the listener immediately with the preference's
        // current value.
        Object value = null;
        switch (type) {
            case Boolean:
                value = mSharedPreferences.getBoolean(preference.getKey(), false);
                break;
            case Long:
                value = mSharedPreferences.getLong(preference.getKey(), 0);
                break;
            case Int:
                value = mSharedPreferences.getInt(preference.getKey(), 0);
                break;
            case Float:
                value = mSharedPreferences.getFloat(preference.getKey(), 0);
                break;
            case String:
            default:
                value = mSharedPreferences.getString(preference.getKey(), "");
                break;
        }
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference, value);
    }

    public enum ValueType {
        String,
        Int,
        Long,
        Float,
        Boolean,
    }

    protected MyPreference mMyPreference;
    protected SharedPreferences mSharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mMyPreference = MyPreference.get(getActivity());
        mSharedPreferences = mMyPreference.getSharedPreferences();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
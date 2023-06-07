/*
 * Copyright (C) 2024 Yet Another AOSP Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.yaap.spesparts.saturation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceManager;

import java.io.IOException;

import org.yaap.spesparts.misc.Constants;
import org.yaap.spesparts.preferences.CustomSeekBarPreference;
import org.yaap.spesparts.R;

public class Saturation extends PreferenceFragment
        implements Preference.OnPreferenceChangeListener {
    private static final String TAG = Saturation.class.getSimpleName();

    // Saturation preference
    private CustomSeekBarPreference mSaturationPreference;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.saturation);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        // Saturation preference
        mSaturationPreference =  (CustomSeekBarPreference) findPreference(Constants.KEY_SATURATION);
        mSaturationPreference.setOnPreferenceChangeListener(this);
        int seekBarValue = sharedPrefs.getInt(Constants.KEY_SATURATION, 100);
        updateSaturation(seekBarValue);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        // Saturation preference
        if (preference == mSaturationPreference) {
            int seekBarValue = (Integer) newValue;
            updateSaturation(seekBarValue);
            return true;
        }
        return false;
    }

    public static void updateSaturation(int seekBarValue) {
        float saturation;
        if (seekBarValue == 100) {
            saturation = 1.001f;
        } else {
            saturation = seekBarValue / 100.0f;
        }

        try {
            Runtime.getRuntime().exec("service call SurfaceFlinger 1022 f " + saturation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Saturation preference
    public static void restoreSaturationSetting(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        int seekBarValue = sharedPrefs.getInt(Constants.KEY_SATURATION, 100);
        updateSaturation(seekBarValue);
    }
}

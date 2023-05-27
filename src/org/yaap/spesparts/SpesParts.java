/*
 * Copyright (C) 2018-2022 crDroid Android Project
 *               2024 Yet Another AOSP Project
 *               2024 GuidixX
 * SPDX-License-Identifier: Apache-2.0
 */

package org.yaap.speparts;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceManager;
import androidx.preference.Preference;
import androidx.preference.SwitchPreference;

import org.yaap.spesparts.misc.Constants;
import org.yaap.spesparts.R;
import org.yaap.spesparts.utils.Utils;

public class SpesParts extends PreferenceFragment
        implements Preference.OnPreferenceChangeListener {
    private static final String TAG = SpesParts.class.getSimpleName();

    // Power efficient workqueue switch
    private SwitchPreference mPowerEfficientWorkqueueModeSwitch;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.main);
        SharedPreferences prefs = getActivity().getSharedPreferences("main",
                Activity.MODE_PRIVATE);
        if (savedInstanceState == null && !prefs.getBoolean("first_warning_shown", false)) {
            showWarning();
        }

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        Context context = getContext();

        // Power efficient workqueue switch
        mPowerEfficientWorkqueueModeSwitch = (SwitchPreference) findPreference(Constants.KEY_POWER_EFFICIENT_WORKQUEUE);
        if (Utils.isFileWritable(Constants.NODE_POWER_EFFICIENT_WORKQUEUE)) {
            mPowerEfficientWorkqueueModeSwitch.setEnabled(true);
            mPowerEfficientWorkqueueModeSwitch.setChecked(sharedPrefs.getBoolean(Constants.KEY_POWER_EFFICIENT_WORKQUEUE, false));
            mPowerEfficientWorkqueueModeSwitch.setOnPreferenceChangeListener(this);
        } else {
            mPowerEfficientWorkqueueModeSwitch.setEnabled(false);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        // Power efficient workqueue switch
        if (preference == mPowerEfficientWorkqueueModeSwitch) {
            boolean enabled = (Boolean) newValue;
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
            sharedPrefs.edit().putBoolean(Constants.KEY_POWER_EFFICIENT_WORKQUEUE, enabled).commit();
            Utils.writeValue(Constants.NODE_POWER_EFFICIENT_WORKQUEUE, enabled ? "1" : "0");
            return true;
        return false;
    }

    // Power efficient workqueue switch
    public static void restorePowerEfficientWorkqueueSetting(Context context) {
        if (Utils.isFileWritable(Constants.NODE_POWER_EFFICIENT_WORKQUEUE)) {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
            boolean value = sharedPrefs.getBoolean(Constants.KEY_POWER_EFFICIENT_WORKQUEUE, false);
            Utils.writeValue(Constants.NODE_POWER_EFFICIENT_WORKQUEUE, value ? "1" : "0");
        }
    }

    // First launch warning dialog
    public static class WarningDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.spes_parts_warning_title)
                    .setMessage(R.string.spes_parts_warning_text)
                    .setNegativeButton(R.string.spes_parts_dialog, (dialog, which) -> dialog.cancel())
                    .create();
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            getActivity().getSharedPreferences("main", Activity.MODE_PRIVATE)
                    .edit()
                    .putBoolean("first_warning_shown", true)
                    .commit();
        }
    }

    private void showWarning() {
        WarningDialogFragment fragment = new WarningDialogFragment();
        fragment.show(getFragmentManager(), "warning_dialog");
    }
}

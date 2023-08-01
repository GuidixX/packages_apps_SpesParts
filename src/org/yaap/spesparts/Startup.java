/*
 * Copyright (C) 2018-2022 crDroid Android Project
 *               2024 Yet Another AOSP Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.yaap.spesparts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.yaap.spesparts.saturation.Saturation;
import org.yaap.spesparts.utils.ComponentUtils;

public class Startup extends BroadcastReceiver {

    private static final String TAG = Startup.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();

            // SpesParts
            SpesParts.restorePowerEfficientWorkqueueSetting(context);
            Saturation.restoreSaturationSetting(context);
    }
}

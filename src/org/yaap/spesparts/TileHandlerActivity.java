/*
 * Copyright (C) 2024 Yet Another AOSP Project
 * SPDX-License-Identifier: MIT
 */

package org.yaap.spesparts;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.service.quicksettings.TileService;
import android.util.Log;

import org.yaap.spesparts.saturation.SaturationActivity;
import org.yaap.spesparts.saturation.SaturationTileService;

public final class TileHandlerActivity extends Activity {
    private static final String TAG = "TileHandlerActivity";
    @Override
    protected void onCreate(final android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent intent = getIntent();
        try {
            if (android.service.quicksettings.TileService.ACTION_QS_TILE_PREFERENCES.equals(intent.getAction())) {
                final ComponentName qsTile =
                        intent.getParcelableExtra(Intent.EXTRA_COMPONENT_NAME);
                final String qsName = qsTile.getClassName();
                final Intent aIntent = new Intent();

                if (qsName.equals(SaturationTileService.class.getName())) {
                    aIntent.setClass(this, SaturationActivity.class);
                } else {
                    aIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    aIntent.setData(Uri.fromParts("package", qsTile.getPackageName(), null));
                }

                aIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(aIntent);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error handling intent: " + intent, e);
        } finally {
            finish();
        }
    }
}

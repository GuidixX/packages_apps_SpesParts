/*
 * Copyright (C) 2024 Yet Another AOSP Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.yaap.spesparts.services;

import android.content.SharedPreferences;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import androidx.preference.PreferenceManager;

import org.yaap.spesparts.misc.Constants;
import org.yaap.spesparts.utils.Utils;

public class PEWQTileSerice extends TileService {

    private void updateTile(boolean enabled) {
        final Tile tile = getQsTile();
        tile.setState(enabled ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
        tile.updateTile();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        updateTile(sharedPrefs.getBoolean(Constants.KEY_POWER_EFFICIENT_WORKQUEUE, false));
    }

    @Override
    public void onStopListening() {
        super.onStopListening();
    }

    @Override
    public void onClick() {
        super.onClick();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        final boolean enabled = !(sharedPrefs.getBoolean(Constants.KEY_POWER_EFFICIENT_WORKQUEUE, false));
        Utils.writeValue(Constants.NODE_POWER_EFFICIENT_WORKQUEUE, enabled ? "1" : "0");
        sharedPrefs.edit().putBoolean(Constants.KEY_POWER_EFFICIENT_WORKQUEUE, enabled).commit();
        updateTile(enabled);
    }
}

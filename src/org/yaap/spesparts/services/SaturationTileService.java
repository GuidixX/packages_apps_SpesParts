/*
 * Copyright (C) 2024 Yet Another AOSP Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.yaap.spesparts.services;

import android.app.PendingIntent;
import android.content.Intent;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

import org.yaap.spesparts.saturation.SaturationActivity;

public class SaturationTileService extends TileService {

    private void updateTile() {
        final Tile tile = getQsTile();
        tile.setState(Tile.STATE_ACTIVE);
        tile.updateTile();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        updateTile();
    }

    @Override
    public void onClick() {
        super.onClick();
        Intent intent = new Intent(this, SaturationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        startActivityAndCollapse(pendingIntent);
    }
}

package com.codarini.ojeda.parcial1.receiver;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.RequiresPermission;

import com.codarini.ojeda.parcial1.utils.NotificationUtils.NotificationUtils;

public class MedicacionReceiver extends BroadcastReceiver {

    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_BODY  = "extra_body";
    public static final String EXTRA_ID    = "extra_id";

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationUtils.createChannel(context);

        String title = intent.getStringExtra(EXTRA_TITLE);
        String body = intent.getStringExtra(EXTRA_BODY);
        int id = intent.getIntExtra(EXTRA_ID, 1001);

        if (title == null) title = "MedTrack";
        if (body == null) body = "Hora de tu medicación";

        NotificationUtils.show(context, id, title, body);
    }
}
package com.codarini.ojeda.parcial1.utils.MedicacionAlarmSheduler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.codarini.ojeda.parcial1.receiver.MedicacionReceiver;
import com.codarini.ojeda.parcial1.utils.NotificationUtils.NotificationUtils;

import java.util.Calendar;
import java.util.Locale;

public class MedicacionAlarmScheduler {

    // Convierte "HH:mm" a Calendar (hoy, o mañana si ya pasó)
    private static Calendar nextTrigger(String horaHHmm) {
        String[] parts = horaHHmm.split(":");
        int hh = Integer.parseInt(parts[0]);
        int mm = Integer.parseInt(parts[1]);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hh);
        c.set(Calendar.MINUTE, mm);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        // Si ya pasó la hora, lo programamos para mañana
        if (c.getTimeInMillis() <= System.currentTimeMillis()) {
            c.add(Calendar.DAY_OF_YEAR, 1);
        }
        return c;
    }

    private static PendingIntent buildPI(Context ctx, int notifId, String title, String body) {
        Intent i = new Intent(ctx, MedicacionReceiver.class);
        i.putExtra(MedicacionReceiver.EXTRA_ID, notifId);
        i.putExtra(MedicacionReceiver.EXTRA_TITLE, title);
        i.putExtra(MedicacionReceiver.EXTRA_BODY, body);

        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flags |= PendingIntent.FLAG_IMMUTABLE;
        }

        // requestCode = notifId para que sea único por medicamento
        return PendingIntent.getBroadcast(ctx, notifId, i, flags);
    }

    public static void schedule(Context ctx, int notifId, String horaHHmm, String nombreMed, String extraInfo) {
        NotificationUtils.createChannel(ctx);

        AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        if (am == null) return;

        String title = "Hora de tu medicación";
        String body = String.format(Locale.getDefault(),
                "%s %s", nombreMed, (extraInfo != null ? ("- " + extraInfo) : "")).trim();

        PendingIntent pi = buildPI(ctx, notifId, title, body);
        Calendar when = nextTrigger(horaHHmm);

        // Exacto (si el celu está en Doze, esto igual intenta ser exacto)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, when.getTimeInMillis(), pi);
        } else {
            am.setExact(AlarmManager.RTC_WAKEUP, when.getTimeInMillis(), pi);
        }
    }

    public static void cancel(Context ctx, int notifId) {
        AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        if (am == null) return;

        PendingIntent pi = buildPI(ctx, notifId, "x", "x");
        am.cancel(pi);
    }
}
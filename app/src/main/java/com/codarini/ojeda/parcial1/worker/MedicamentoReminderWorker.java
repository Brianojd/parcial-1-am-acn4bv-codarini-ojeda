package com.codarini.ojeda.parcial1.worker;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.codarini.ojeda.parcial1.utils.NotificationUtils.NotificationUtils;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MedicamentoReminderWorker extends Worker {

    public static final String KEY_UID = "uid";
    public static final String KEY_DOC_ID = "docId";
    public static final String KEY_NOMBRE = "nombre";

    public MedicamentoReminderWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        String uid = getInputData().getString(KEY_UID);
        String docId = getInputData().getString(KEY_DOC_ID);
        String nombre = getInputData().getString(KEY_NOMBRE);

        if (uid == null || docId == null) return Result.failure();

        // Notificación
        var notif = NotificationUtils.build(getApplicationContext(),
                "Hora de tomar medicación",
                nombre != null ? nombre : "Tenés una medicación pendiente"
        ).build();

        if (ActivityCompat.checkSelfPermission(
                getApplicationContext(),
                Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED) {
            return Result.failure();
        }

        NotificationManagerCompat.from(getApplicationContext())
                .notify(docId.hashCode(), notif);

        // Marcar como no pendiente (esto hace que tu listener lo saque)
        Map<String, Object> upd = new HashMap<>();
        upd.put("pendiente", false);

        FirebaseFirestore.getInstance()
                .collection("usuarios").document(uid)
                .collection("medicaciones").document(docId)
                .update(upd);

        return Result.success();
    }

    public static void programar(Context ctx, String uid, String docId, long whenMillis, String nombre) {
        long delay = whenMillis - System.currentTimeMillis();
        if (delay < 0) delay = 0;

        Data data = new Data.Builder()
                .putString(KEY_UID, uid)
                .putString(KEY_DOC_ID, docId)
                .putString(KEY_NOMBRE, nombre)
                .build();

        OneTimeWorkRequest req = new OneTimeWorkRequest.Builder(MedicamentoReminderWorker.class)
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .setInputData(data)
                .build();

        WorkManager.getInstance(ctx).enqueue(req);
    }
}
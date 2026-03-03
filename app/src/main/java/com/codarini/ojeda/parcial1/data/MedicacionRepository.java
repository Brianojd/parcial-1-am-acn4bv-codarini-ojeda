package com.codarini.ojeda.parcial1.data;

import java.util.ArrayList;
import java.util.List;
import com.codarini.ojeda.parcial1.model.MedicamentoItem;
import com.codarini.ojeda.parcial1.utils.DateUtils.DateUtils;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MedicacionRepository {

    public interface MedicamentosCallback {
        void onResult(List<MedicamentoItem> items);
    }

    public void escucharPendientesDeHoy(String uid, MedicamentosCallback cb) {
        long start = DateUtils.startOfTodayMillis();
        long end   = DateUtils.endOfTodayMillis();

        FirebaseFirestore.getInstance()
                .collection("usuarios").document(uid)
                .collection("medicaciones")
                .whereEqualTo("pendiente", true)
                .whereGreaterThanOrEqualTo("programadaParaMillis", start)
                .whereLessThan("programadaParaMillis", end)
                .orderBy("programadaParaMillis")
                .addSnapshotListener((snap, e) -> {
                    if (e != null || snap == null) return;

                    List<MedicamentoItem> list = new ArrayList<>();
                    for (DocumentSnapshot d : snap.getDocuments()) {
                        MedicamentoItem m = d.toObject(MedicamentoItem.class);
                        if (m != null) {
                            m.id = d.getId();
                            list.add(m);
                        }
                    }
                    cb.onResult(list);
                });
    }
}
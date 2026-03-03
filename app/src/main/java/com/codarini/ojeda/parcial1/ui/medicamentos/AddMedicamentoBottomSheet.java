package com.codarini.ojeda.parcial1.ui.medicamentos;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.codarini.ojeda.parcial1.R;
import com.codarini.ojeda.parcial1.worker.MedicamentoReminderWorker;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddMedicamentoBottomSheet extends BottomSheetDialogFragment {

    private EditText etNombre, etForma, etConc, etUnidad, etDosis, etHora, etInstr;
    private Button btnGuardar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottomsheet_add_medicamento, container, false);

        etNombre = v.findViewById(R.id.etnombre);
        etForma  = v.findViewById(R.id.etforma);
        etConc   = v.findViewById(R.id.etconcentracion);
        etUnidad = v.findViewById(R.id.etunidad);
        etDosis  = v.findViewById(R.id.etdosis);
        etHora   = v.findViewById(R.id.ethora);
        etInstr  = v.findViewById(R.id.etinstr);
        btnGuardar = v.findViewById(R.id.btnguardar);

        btnGuardar.setOnClickListener(x -> guardar());

        return v;
    }

    private void guardar() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        String nombre = etNombre.getText().toString().trim();
        String forma  = etForma.getText().toString().trim();
        String concS  = etConc.getText().toString().trim();
        String unidad = etUnidad.getText().toString().trim();
        String dosisS = etDosis.getText().toString().trim();
        String hora   = etHora.getText().toString().trim();
        String instr  = etInstr.getText().toString().trim();

        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(hora)) {
            Toast.makeText(requireContext(), "Nombre y hora son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        Long programadaMillis = parseHoraDeHoyMillis(hora);
        if (programadaMillis == null) {
            Toast.makeText(requireContext(), "Hora inválida. Usá HH:mm (ej 08:30)", Toast.LENGTH_SHORT).show();
            return;
        }

        Double conc = TextUtils.isEmpty(concS) ? null : Double.parseDouble(concS);
        Double dosis = TextUtils.isEmpty(dosisS) ? null : Double.parseDouble(dosisS);

        String docId = UUID.randomUUID().toString();

        Map<String, Object> data = new HashMap<>();
        data.put("nombre", nombre);
        data.put("forma", forma);
        data.put("concentracion", conc);
        data.put("unidad", unidad);
        data.put("dosis", dosis);
        data.put("hora", hora);
        data.put("instrucciones", instr);

        data.put("pendiente", true);
        data.put("programadaParaMillis", programadaMillis);
        data.put("creadoEnMillis", System.currentTimeMillis());

        FirebaseFirestore.getInstance()
                .collection("usuarios").document(uid)
                .collection("medicaciones").document(docId)
                .set(data)
                .addOnSuccessListener(a -> {
                    // Programar notificación
                    MedicamentoReminderWorker.programar(requireContext(), uid, docId, programadaMillis, nombre);
                    Toast.makeText(requireContext(), "Guardado", Toast.LENGTH_SHORT).show();
                    dismiss();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(requireContext(), "Error al guardar", Toast.LENGTH_SHORT).show()
                );
    }

    private Long parseHoraDeHoyMillis(String hhmm) {
        try {
            String[] parts = hhmm.split(":");
            if (parts.length != 2) return null;

            int h = Integer.parseInt(parts[0]);
            int m = Integer.parseInt(parts[1]);
            if (h < 0 || h > 23 || m < 0 || m > 59) return null;

            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, h);
            c.set(Calendar.MINUTE, m);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);

            return c.getTimeInMillis();
        } catch (Exception ex) {
            return null;
        }
    }
}
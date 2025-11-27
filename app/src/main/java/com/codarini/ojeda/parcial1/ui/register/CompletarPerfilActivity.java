package com.codarini.ojeda.parcial1.ui.register;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.codarini.ojeda.parcial1.R;
import com.codarini.ojeda.parcial1.ui.home.HomeActivity;
import com.codarini.ojeda.parcial1.utils.validation.ProfileValidator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CompletarPerfilActivity extends AppCompatActivity {

    private TextView textSaludo, txtFechaNac;
    private EditText editPeso, editAltura;
    private Spinner spinnerSexo, spinnerActividad;
    private Button btnGuardar;

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_completar_perfil);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        textSaludo = findViewById(R.id.textSaludo);
        txtFechaNac = findViewById(R.id.editFechaNacimiento);
        editPeso = findViewById(R.id.editPeso);
        editAltura = findViewById(R.id.editAltura);
        spinnerSexo = findViewById(R.id.spinnerSexo);
        spinnerActividad = findViewById(R.id.spinnerActividad);
        btnGuardar = findViewById(R.id.btnGuardar);


        String uid = auth.getCurrentUser().getUid();
        db.collection("usuarios").document(uid)
                .get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        String nombre = doc.getString("nombre");
                        if (nombre != null)
                            textSaludo.setText("Hola, " + nombre);
                    }
                });


        ArrayAdapter<CharSequence> adapterSexo = ArrayAdapter.createFromResource(
                this,
                R.array.sexo_array,
                android.R.layout.simple_spinner_dropdown_item
        );
        spinnerSexo.setAdapter(adapterSexo);


        ArrayAdapter<CharSequence> adapterActividad = ArrayAdapter.createFromResource(
                this,
                R.array.actividad_array,
                android.R.layout.simple_spinner_dropdown_item
        );
        spinnerActividad.setAdapter(adapterActividad);

        // -----------------------------------------------------
        // DatePicker para fecha de nacimiento
        // -----------------------------------------------------
        txtFechaNac.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dp = new DatePickerDialog(
                    this,
                    (DatePicker view, int y, int m, int d) -> {
                        txtFechaNac.setText(d + "/" + (m+1) + "/" + y);
                        calcularEdad(y, m+1, d);
                    }, year, month, day
            );
            dp.show();
        });


        btnGuardar.setOnClickListener(v -> guardarPerfil());
    }


    private int calcularEdad(int year, int month, int day) {
        Calendar nacimiento = Calendar.getInstance();
        nacimiento.set(year, month - 1, day);

        Calendar hoy = Calendar.getInstance();

        int edad = hoy.get(Calendar.YEAR) - nacimiento.get(Calendar.YEAR);

        if (hoy.get(Calendar.DAY_OF_YEAR) < nacimiento.get(Calendar.DAY_OF_YEAR))
            edad--;

        return edad;
    }

    private int calcularEdadDesdeFecha(String fecha) {
        String[] partes = fecha.split("/");
        int d = Integer.parseInt(partes[0]);
        int m = Integer.parseInt(partes[1]);
        int y = Integer.parseInt(partes[2]);
        return calcularEdad(y, m, d);
    }

    // ---------------------------------------------------------
    // Guardar datos en Firestore
    // ---------------------------------------------------------
    private void guardarPerfil() {
        String uid = auth.getCurrentUser().getUid();

        String fechaNac = txtFechaNac.getText().toString();
        String peso = editPeso.getText().toString();
        String altura = editAltura.getText().toString();
        String sexo = spinnerSexo.getSelectedItem().toString();
        String actividad = spinnerActividad.getSelectedItem().toString();

        if (fechaNac.isEmpty() || peso.isEmpty() || altura.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!ProfileValidator.isValidFecha(fechaNac)) {
            Toast.makeText(this, "Seleccioná tu fecha de nacimiento", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!ProfileValidator.isValidPeso(peso)) {
            editPeso.setError("Peso inválido");
            editPeso.requestFocus();
            return;
        }

        if (!ProfileValidator.isValidAltura(altura)) {
            editAltura.setError("Altura inválida");
            editAltura.requestFocus();
            return;
        }

        if (spinnerSexo.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Seleccioná tu sexo", Toast.LENGTH_SHORT).show();
            return;
        }

        if (spinnerActividad.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Seleccioná tu nivel de actividad", Toast.LENGTH_SHORT).show();
            return;
        }
        int edad = calcularEdadDesdeFecha(fechaNac);

        Map<String, Object> data = new HashMap<>();
        data.put("fecha_nacimiento", fechaNac);
        data.put("edad", edad);
        data.put("peso", Float.parseFloat(peso));
        data.put("altura_cm", Integer.parseInt(altura));
        data.put("sexo", sexo);
        data.put("nivel_actividad", actividad);
        data.put("activo", true);

        db.collection("usuarios").document(uid)
                .update(data)
                .addOnSuccessListener(a -> {
                    Toast.makeText(this, "Perfil completado", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, HomeActivity.class));
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
                );
    }

}

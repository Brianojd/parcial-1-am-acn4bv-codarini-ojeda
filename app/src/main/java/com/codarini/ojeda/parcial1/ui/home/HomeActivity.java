package com.codarini.ojeda.parcial1.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.codarini.ojeda.parcial1.R;
import com.codarini.ojeda.parcial1.model.Usuario;
import com.codarini.ojeda.parcial1.utils.EdadUtils.EdadUtils;
import com.codarini.ojeda.parcial1.utils.ImcUtils.ImcUtils;
import com.codarini.ojeda.parcial1.viewmodel.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends AppCompatActivity {

    // ---- Layout principal
    private TextView tvHeader;
    private RecyclerView rvMedicamentos;
    private FloatingActionButton fabAgregar;

    // ---- Resumen salud (del include)
    private TextView tvEdad;
    private TextView tvAltura;
    private TextView tvPeso;
    private TextView tvImc;
    private ImageView ivFace;


    private TextView tvCategoria;
    private LinearLayout rowMuyExtrema;
    private LinearLayout rowExtrema;
    private LinearLayout rowDelgadez;
    private LinearLayout rowNormal;
    private LinearLayout rowSobrepeso;
    private LinearLayout rowOb1;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // -----------------------------
        // Bind layout principal
        // -----------------------------
        tvHeader = findViewById(R.id.tv_header);
        rvMedicamentos = findViewById(R.id.rv_medicamentos);
        fabAgregar = findViewById(R.id.fab_agregar);


        // -----------------------------
        // Bind include: view_resumen_salud
        // -----------------------------
        View resumenSalud = findViewById(R.id.include_resumen_salud);
        ivFace = resumenSalud.findViewById(R.id.iv_face);
        if (ivFace == null) {
            Log.e("HomeActivity", "ivFace es NULL. Revisar include y id");
            return;
        }


        // ⬆️ este ID lo tenés que poner en el root del include

        tvEdad = resumenSalud.findViewById(R.id.tv_edad);
        tvAltura = resumenSalud.findViewById(R.id.tv_altura);
        tvPeso = resumenSalud.findViewById(R.id.tv_peso);
        tvImc = resumenSalud.findViewById(R.id.tv_imc);
        tvCategoria = resumenSalud.findViewById(R.id.tv_categoria);


        rowMuyExtrema = resumenSalud.findViewById(R.id.row_delgadez_muy_extrema);
        rowExtrema    = resumenSalud.findViewById(R.id.row_delgadez_extrema);
        rowDelgadez   = resumenSalud.findViewById(R.id.row_delgadez);
        rowNormal     = resumenSalud.findViewById(R.id.row_normal);
        rowSobrepeso  = resumenSalud.findViewById(R.id.row_sobrepeso);
        rowOb1        = resumenSalud.findViewById(R.id.row_obesidad_1);



        // -----------------------------
        // ViewModel
        // -----------------------------
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        cargarUsuario();
        observarUsuario();
    }
    private void clearRows(LinearLayout... rows) {
        for (LinearLayout row : rows) {
            row.setBackground(null);
        }
    }
    private void highlightRow(LinearLayout row) {
        row.setBackgroundResource(android.R.color.darker_gray);
    }

    private void cargarUsuario() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore.getInstance()
                .collection("usuarios")
                .document(uid)
                .get()
                .addOnSuccessListener(doc -> {
                    Usuario usuario = doc.toObject(Usuario.class);
                    userViewModel.setUsuario(usuario);
                });
    }
    private void aplicarEstadoImc(
            double imc,
            ImageView ivFace,
            TextView tvImc,
            TextView tvCategoria,
            LinearLayout... rows
    ) {
        clearRows(rows);

        if (imc <= 16.9) {
            ivFace.setImageResource(R.drawable.ic_face_sad);
            tvImc.setTextColor(getColor(R.color.red_danger));
            tvCategoria.setTextColor(getColor(R.color.red_danger));
            highlightRow(rows[0]); // muy extrema / extrema
        }
        else if (imc <= 18.4) {
            ivFace.setImageResource(R.drawable.ic_face_neutral);
            tvImc.setTextColor(getColor(R.color.yellow_warning));
            tvCategoria.setTextColor(getColor(R.color.yellow_warning));
            highlightRow(rows[2]); // delgadez
        }
        else if (imc <= 24.9) {
            ivFace.setImageResource(R.drawable.ic_face_happy);
            tvImc.setTextColor(getColor(R.color.green_healthy));
            tvCategoria.setTextColor(getColor(R.color.green_healthy));
            highlightRow(rows[3]); // normal
        }
        else if (imc <= 29.9) {
            ivFace.setImageResource(R.drawable.ic_face_neutral);
            tvImc.setTextColor(getColor(R.color.yellow_warning));
            tvCategoria.setTextColor(getColor(R.color.yellow_warning));
            highlightRow(rows[4]); // sobrepeso
        }
        else {
            ivFace.setImageResource(R.drawable.ic_face_sad);
            tvImc.setTextColor(getColor(R.color.red_danger));
            tvCategoria.setTextColor(getColor(R.color.red_danger));
            highlightRow(rows[5]); // obesidad I
        }
    }

    private void observarUsuario() {
        userViewModel.getUsuario().observe(this, usuario -> {
            if (usuario == null) return;

            int edad = EdadUtils.calcular(usuario.getFecha_nacimiento());
            double imc = ImcUtils.calcular(usuario.getPeso(), usuario.getAltura_cm());

            tvEdad.setText(String.valueOf(edad));
            tvAltura.setText(usuario.getAltura_cm() + " cm");
            tvPeso.setText(usuario.getPeso() + " kg");
            tvImc.setText(String.format("%.1f", imc));
            tvCategoria.setText(ImcUtils.categoria(imc));

            aplicarEstadoImc(
                    imc,
                    ivFace,
                    tvImc,
                    tvCategoria,
                    rowMuyExtrema,
                    rowExtrema,
                    rowDelgadez,
                    rowNormal,
                    rowSobrepeso,
                    rowOb1
            );
        });
    }


}

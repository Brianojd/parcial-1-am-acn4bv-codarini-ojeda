package com.codarini.ojeda.parcial1;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.codarini.ojeda.parcial1.ui.home.HomeActivity;
import com.codarini.ojeda.parcial1.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Opción 1: SIN layout en pantalla (más rápido)
        // setContentView(R.layout.activity_main);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // -------------------------
            // Usuario logueado
            // -------------------------
            startActivity(new Intent(this, HomeActivity.class));
        } else {
            // -------------------------
            // Usuario NO logueado
            // -------------------------
            startActivity(new Intent(this, LoginActivity.class));
        }

        // Cerramos esta Activity para que no vuelva atrás
        finish();
    }
}

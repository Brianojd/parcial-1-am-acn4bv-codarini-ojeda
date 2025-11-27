package com.codarini.ojeda.parcial1;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.codarini.ojeda.parcial1.ui.home.HomeActivity;
import com.codarini.ojeda.parcial1.ui.login.LoginActivity;
import com.codarini.ojeda.parcial1.ui.register.CompletarPerfilActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        if (user == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }


        String uid = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("usuarios").document(uid).get()
                .addOnSuccessListener(doc -> {


                    if (!doc.exists()) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(this, LoginActivity.class));
                        finish();
                        return;
                    }


                    Boolean activo = doc.getBoolean("activo");
                    if (activo == null) activo = false;


                    if (activo) {
                        startActivity(new Intent(this, HomeActivity.class));
                    }

                    else {
                        startActivity(new Intent(this, CompletarPerfilActivity.class));
                    }

                    finish();
                })
                .addOnFailureListener(e -> {

                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                });
    }
}

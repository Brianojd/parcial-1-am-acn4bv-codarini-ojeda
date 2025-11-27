package com.codarini.ojeda.parcial1.ui.register;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.codarini.ojeda.parcial1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText editName, editEmail, editPassword;
    private Button btnRegister;
    private TextView btnLogin;  // 🔹 Nuevo botón para volver al login

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Firebase
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Bind UI
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);  // 🔹 enlace con el TextView nuevo

        // 🔹 Listener: si el usuario ya tiene cuenta → volver al login
        btnLogin.setOnClickListener(v -> finish());

        btnRegister.setOnClickListener(v -> {
            String name = editName.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            // Validaciones
            if (name.isEmpty()) {
                editName.setError(getString(R.string.error_complete_fields));
                editName.requestFocus();
                return;
            }

            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editEmail.setError(getString(R.string.error_invalid_email));
                editEmail.requestFocus();
                return;
            }

            if (password.length() < 6) {
                editPassword.setError(getString(R.string.error_short_password));
                editPassword.requestFocus();
                return;
            }

            registerUser(name, email, password);
        });
    }

    // 🔹 Crear usuario, guardar info, cerrar actividad
    private void registerUser(String name, String email, String password) {

        btnRegister.setEnabled(false);

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if (!task.isSuccessful()) {
                        btnRegister.setEnabled(true);
                        Toast.makeText(this,
                                "Error: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String uid = auth.getCurrentUser().getUid();

                    Map<String, Object> userData = new HashMap<>();
                    userData.put("nombre", name);
                    userData.put("email", email);
                    userData.put("activo", false);

                    db.collection("usuarios").document(uid)
                            .set(userData)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(this,
                                        getString(R.string.register_success),
                                        Toast.LENGTH_SHORT).show();

                                auth.signOut();

                                finish(); // Volver al login
                            })
                            .addOnFailureListener(e -> {
                                btnRegister.setEnabled(true);
                                Toast.makeText(this,
                                        getString(R.string.register_save_error),
                                        Toast.LENGTH_SHORT).show();
                            });
                });
    }
}

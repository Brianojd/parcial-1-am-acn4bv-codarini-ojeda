package com.codarini.ojeda.parcial1.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.TextView;   // 🔹 IMPORTANTE

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.codarini.ojeda.parcial1.R;
import com.codarini.ojeda.parcial1.data.AuthRepository;
import com.codarini.ojeda.parcial1.ui.home.HomeActivity;
import com.codarini.ojeda.parcial1.ui.register.RegisterActivity;  // 🔹 IMPORTANTE
import com.codarini.ojeda.parcial1.viewmodel.LoginViewModel;
import com.codarini.ojeda.parcial1.viewmodel.LoginViewModelFactory;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button sendData;
    private ProgressBar loading;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // -----------------------------
        // 1. Vincular vistas
        // -----------------------------
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        sendData = findViewById(R.id.sendData);
        loading = findViewById(R.id.loading);

        // 🔹 Nuevo: botón para ir al registro
        TextView btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        // -----------------------------
        // 2. Crear Repository
        // -----------------------------
        FirebaseAuth auth = FirebaseAuth.getInstance();
        AuthRepository repository = new AuthRepository(auth);

        // -----------------------------
        // 3. Crear ViewModel con Factory
        // -----------------------------
        LoginViewModelFactory factory = new LoginViewModelFactory(repository);
        viewModel = new ViewModelProvider(this, factory).get(LoginViewModel.class);

        // -----------------------------
        // 4. Observadores de LiveData
        // -----------------------------
        setupObservers();

        // -----------------------------
        // 5. Botón Login
        // -----------------------------
        sendData.setOnClickListener(v -> {
            String emailValue = email.getText().toString().trim();
            String passValue = password.getText().toString().trim();

            viewModel.login(emailValue, passValue);
        });
    }

    private void setupObservers() {

        viewModel.getLoginSuccess().observe(this, success -> {
            if (success) {
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            }
        });

        viewModel.getLoginError().observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });
    }
}

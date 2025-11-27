package com.codarini.ojeda.parcial1.data;

import com.google.firebase.auth.FirebaseAuth;

public class AuthRepository {

    private final FirebaseAuth auth;

    public AuthRepository(FirebaseAuth auth) {
        this.auth = auth;
    }

    public interface LoginCallback {
        void onSuccess();
        void onError(String message);
    }

    public void login(String email, String password, LoginCallback callback) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(result -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }
}

package com.codarini.ojeda.parcial1.data;

import com.codarini.ojeda.parcial1.model.Usuario;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void getUsuario(String uid, OnSuccessListener<Usuario> onSuccess) {
        db.collection("usuarios")
                .document(uid)
                .get()
                .addOnSuccessListener(doc -> {
                    Usuario usuario = doc.toObject(Usuario.class);
                    onSuccess.onSuccess(usuario);
                });
    }
}

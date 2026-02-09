package com.codarini.ojeda.parcial1.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codarini.ojeda.parcial1.model.Usuario;

public class UserViewModel extends ViewModel {

    private final MutableLiveData<Usuario> usuario = new MutableLiveData<>();

    public LiveData<Usuario> getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario u) {
        usuario.setValue(u);
    }
}
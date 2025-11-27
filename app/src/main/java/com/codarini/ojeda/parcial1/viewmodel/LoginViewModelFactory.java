package com.codarini.ojeda.parcial1.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.codarini.ojeda.parcial1.data.AuthRepository;

public class LoginViewModelFactory implements ViewModelProvider.Factory {

    private final AuthRepository repository;

    public LoginViewModelFactory(AuthRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LoginViewModel(repository);
    }
}

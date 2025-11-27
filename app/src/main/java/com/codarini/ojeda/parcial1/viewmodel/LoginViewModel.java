package com.codarini.ojeda.parcial1.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codarini.ojeda.parcial1.data.AuthRepository;

public class LoginViewModel extends ViewModel {

    private final AuthRepository repository;

    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> loginError = new MutableLiveData<>();

    public LoginViewModel(AuthRepository repository) {
        this.repository = repository;
    }

    public LiveData<Boolean> getLoading() { return loading; }
    public LiveData<Boolean> getLoginSuccess() { return loginSuccess; }
    public LiveData<String> getLoginError() { return loginError; }

    public void login(String email, String password) {

        loading.setValue(true);

        repository.login(email, password, new AuthRepository.LoginCallback() {
            @Override
            public void onSuccess() {
                loading.postValue(false);
                loginSuccess.postValue(true);
            }

            @Override
            public void onError(String message) {
                loading.postValue(false);
                loginError.postValue(message);
            }
        });
    }
}

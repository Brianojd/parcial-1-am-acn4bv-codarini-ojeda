package com.codarini.ojeda.parcial1.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;
import com.codarini.ojeda.parcial1.data.MedicacionRepository;
import com.codarini.ojeda.parcial1.model.MedicamentoItem;
import com.codarini.ojeda.parcial1.utils.MedicacionAlarmSheduler.MedicacionAlarmScheduler;

public class MedicamentosViewModel extends ViewModel {

    private final MedicacionRepository repo = new MedicacionRepository();

    private final MutableLiveData<List<MedicamentoItem>> _medicamentos =
            new MutableLiveData<>(new ArrayList<>());
    public LiveData<List<MedicamentoItem>> medicamentos = _medicamentos;

    public void escucharPendientesDeHoy(String uid) {
        repo.escucharPendientesDeHoy(uid, items -> _medicamentos.setValue(items));
    }
}

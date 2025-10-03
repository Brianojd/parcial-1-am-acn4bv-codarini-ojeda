package com.codarini.ojeda.parcial1.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codarini.ojeda.parcial1.data.MedicacionRepository;
import com.codarini.ojeda.parcial1.model.MedicamentoItem;

import java.util.ArrayList;
import java.util.List;

public class MedicamentosViewModel extends ViewModel {
    private final MedicacionRepository repo = new MedicacionRepository();
    private final MutableLiveData<List<MedicamentoItem>> _items = new MutableLiveData<>();
    public LiveData<List<MedicamentoItem>> items = _items;

    public void loadNoIniciadas(long usuarioId) {
        _items.setValue(repo.getNoIniciadasHoy(usuarioId));
    }

    public void addMockItem() {
        List<MedicamentoItem> list = _items.getValue();
        if (list == null) list = new ArrayList<>();
        list.add(new MedicamentoItem("Nuevo", "16:00", "Comprimido", 250.0, "mg", 1.0, "Agregado dinámico"));
        _items.setValue(list);
    }
}

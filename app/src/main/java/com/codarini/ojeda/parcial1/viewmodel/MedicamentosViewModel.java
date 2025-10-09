package com.codarini.ojeda.parcial1.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;
import com.codarini.ojeda.parcial1.data.MedicacionRepository;
import com.codarini.ojeda.parcial1.model.MedicamentoItem;

public class MedicamentosViewModel extends ViewModel {

    private final MedicacionRepository repo = new MedicacionRepository();

    private final MutableLiveData<List<MedicamentoItem>> _medicamentos =
            new MutableLiveData<>(new ArrayList<>());
    public LiveData<List<MedicamentoItem>> medicamentos = _medicamentos;

    public void cargarNoIniciadas(long usuarioId) {
        _medicamentos.setValue(repo.obtenerNoIniciadasHoy(usuarioId));
    }


    public void agregarMock() {
        List<MedicamentoItem> lista = _medicamentos.getValue();
        if (lista == null) lista = new ArrayList<>();
        lista.add(new MedicamentoItem("Nuevo medicamento", "Comprimido", 250.0, "mg",
                1.0, "16:00", "Agregado dinámicamente"));
        _medicamentos.setValue(lista);
    }
}

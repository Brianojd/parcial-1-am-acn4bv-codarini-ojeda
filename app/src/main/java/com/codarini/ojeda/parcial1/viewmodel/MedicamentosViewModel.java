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
    private final MutableLiveData<String> _mensaje = new MutableLiveData<>();
    public LiveData<String> mensaje = _mensaje;


    private final MutableLiveData<List<MedicamentoItem>> _medicamentos =
            new MutableLiveData<>(new ArrayList<>());
    public LiveData<List<MedicamentoItem>> medicamentos = _medicamentos;

    public void cargarNoIniciadas(long usuarioId) {
        _medicamentos.setValue(repo.obtenerNoIniciadasHoy(usuarioId));
    }






    private int _mockClicks = 0;



    public void agregarMocks() {
        List<MedicamentoItem> actual = _medicamentos.getValue();
        if (actual == null) actual = new ArrayList<>();

        if (_mockClicks == 0) {

            actual.add(new MedicamentoItem(
                    "Ejemplo A", "Comprimido", 230.0, "prueba",
                    1.0, "10:00", "Agrega solo 1 card para prueba"
            ));
            _mockClicks = 1;
            _medicamentos.setValue(new ArrayList<>(actual));
            return;
        }





    }






}

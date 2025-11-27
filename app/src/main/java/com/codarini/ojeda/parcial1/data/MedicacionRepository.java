package com.codarini.ojeda.parcial1.data;

import java.util.ArrayList;
import java.util.List;
import com.codarini.ojeda.parcial1.model.MedicamentoItem;

public class MedicacionRepository {


    public List<MedicamentoItem> obtenerNoIniciadasHoy(long usuarioId) {
        List<MedicamentoItem> data = new ArrayList<>();
        data.add(new MedicamentoItem("Ibuprofeno", "Comprimido", 400.0, "mg", 1.0, "08:00", "Después de comer"));
        data.add(new MedicamentoItem("Paracetamol", "Comprimido", 500.0, "mg", 1.0, "12:00", ""));
        data.add(new MedicamentoItem("Omeprazol", "Cápsula", 20.0, "mg", null, "20:30", "En ayunas"));
        return data;
    }
}

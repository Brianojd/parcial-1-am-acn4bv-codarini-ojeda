package com.codarini.ojeda.parcial1.data;

import java.util.ArrayList;
import java.util.List;
import com.codarini.ojeda.parcial1.model.MedicamentItem;

public class MedicacionRepository {


    public List<MedicamentItem> getNoIniciadasHoy(long usuarioId) {
        List<MedicamentItem> data = new ArrayList<>();
        data.add(new MedicamentItem("Ibuprofeno", "Comprimido", 400.0, "mg", 1.0, "08:00", "Después de comer"));
        data.add(new MedicamentItem("Paracetamol", "Comprimido", 500.0, "mg", 1.0, "12:00", ""));
        data.add(new MedicamentItem("Omeprazol", "Cápsula", 20.0, "mg", null, "20:30", "En ayunas"));
        return data;
    }

}
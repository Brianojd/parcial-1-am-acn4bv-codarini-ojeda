package com.codarini.ojeda.parcial1.utils.EdadUtils;

import java.util.Calendar;

public class EdadUtils {

    public static int calcular(String fecha) {
        // formato dd/MM/yyyy
        String[] partes = fecha.split("/");
        int d = Integer.parseInt(partes[0]);
        int m = Integer.parseInt(partes[1]) - 1;
        int y = Integer.parseInt(partes[2]);

        Calendar nac = Calendar.getInstance();
        nac.set(y, m, d);

        Calendar hoy = Calendar.getInstance();

        int edad = hoy.get(Calendar.YEAR) - nac.get(Calendar.YEAR);
        if (hoy.get(Calendar.DAY_OF_YEAR) < nac.get(Calendar.DAY_OF_YEAR)) edad--;

        return edad;
    }
}
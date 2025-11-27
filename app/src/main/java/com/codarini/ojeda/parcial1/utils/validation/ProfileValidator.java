package com.codarini.ojeda.parcial1.utils.validation;

public class ProfileValidator {

    public static boolean isValidPeso(String peso) {
        try {
            float p = Float.parseFloat(peso);
            return p >= 20 && p <= 500;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidAltura(String altura) {
        try {
            int a = Integer.parseInt(altura);
            return a >= 50 && a <= 250;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidFecha(String fecha) {
        return fecha != null && !fecha.trim().isEmpty();
    }
}

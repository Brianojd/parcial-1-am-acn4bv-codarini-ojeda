package com.codarini.ojeda.parcial1.utils.ImcUtils;

public class ImcUtils {

    public static double calcular(float peso, int alturaCm) {
        double h = alturaCm / 100.0;
        return peso / (h * h);
    }

    public static String categoria(double imc) {
        if (imc <= 15.9) return "Delgadez muy extrema";
        if (imc <= 16.9) return "Delgadez extrema";
        if (imc <= 18.4) return "Delgadez";
        if (imc <= 24.9) return "Normal";
        if (imc <= 29.9) return "Sobrepeso";
        if (imc <= 34.9) return "Obesidad grado I";
        if (imc <= 39.9) return "Obesidad grado II";
        return "Obesidad grado III";
    }
}
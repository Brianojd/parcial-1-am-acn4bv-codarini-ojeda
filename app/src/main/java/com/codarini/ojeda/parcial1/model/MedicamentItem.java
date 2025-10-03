package com.codarini.ojeda.parcial1.model;

public class MedicamentItem {
    public String nombre;
    public String forma;
    public Double concentracion;
    public String unidad;
    public Double dosis;
    public String hora;
    public String instrucciones;

    public MedicamentItem(String nombre, String forma, Double concentracion, String unidad,
                          Double dosis, String hora, String instrucciones) {
        this.nombre = nombre;
        this.forma = forma;
        this.concentracion = concentracion;
        this.unidad = unidad;
        this.dosis = dosis;
        this.hora = hora;
        this.instrucciones = instrucciones;
    }

    public String buildMeta() {
        StringBuilder sb = new StringBuilder();
        if (forma != null && !forma.isEmpty()) sb.append(forma);
        if (concentracion != null) {
            if (sb.length() > 0) sb.append(" • ");
            sb.append(trim(concentracion)).append(" ").append(unidad == null ? "" : unidad);
        }
        if (dosis != null) {
            if (sb.length() > 0) sb.append(" • ");
            sb.append("Tomar ").append(trim(dosis));
        }
        return sb.toString();
    }

    private String trim(Double v) {
        if (v == null) return "";
        return (Math.floor(v) == v) ? String.valueOf(v.intValue()) : String.valueOf(v);
    }
}

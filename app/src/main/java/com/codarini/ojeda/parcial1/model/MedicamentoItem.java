package com.codarini.ojeda.parcial1.model;

public class MedicamentoItem {
    public String nombre;         // "Ibuprofeno"
    public String forma;          // "Comprimido", "Cápsula"
    public Double concentracion;  // 400.0
    public String unidad;         // "mg"
    public Double dosis;          // 1.0 (opcional)
    public String hora;           // "08:00"
    public String instrucciones;  // "Después de comer" (opcional)

    public MedicamentoItem(String nombre, String forma, Double concentracion, String unidad,
                           Double dosis, String hora, String instrucciones) {
        this.nombre = nombre;
        this.forma = forma;
        this.concentracion = concentracion;
        this.unidad = unidad;
        this.dosis = dosis;
        this.hora = hora;
        this.instrucciones = instrucciones;
    }

    // "Comprimido • 400 mg • Tomar 1"
    public String construirMeta() {
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

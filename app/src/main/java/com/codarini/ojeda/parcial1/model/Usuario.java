package com.codarini.ojeda.parcial1.model;

public class Usuario {

    private String nombre;
    private String email;
    private String fecha_nacimiento;
    private float peso;
    private int altura_cm;
    private String sexo;
    private String nivel_actividad;
    private boolean activo;

    public Usuario() {}

    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getFecha_nacimiento() { return fecha_nacimiento; }
    public float getPeso() { return peso; }
    public int getAltura_cm() { return altura_cm; }
    public String getSexo() { return sexo; }
    public String getNivel_actividad() { return nivel_actividad; }
    public boolean isActivo() { return activo; }
}
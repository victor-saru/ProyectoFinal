package com.example.victo.coachmanager.Entidades;

public class Deporte {

    int id_deporte;
    String nombre;

    public Deporte() {

    }

    public void Deporte(int id_deporte, String nombre){
        this.id_deporte = id_deporte;
        this.nombre = nombre;
    }

    public int getId_deporte() {
        return id_deporte;
    }

    public void setId_deporte(int id_deporte) {
        this.id_deporte = id_deporte;
    }

    public Deporte (String nombre){
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
package com.example.victo.coachmanager.Entidades;

public class Ejercicio {

    String nombre, descripcion;
    int id_entrenador, id_deporte, id_ejercicio;

    public Ejercicio(String nombre, String descripcion, int id_deporte, int id_entrenador, int id_ejercicio){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id_deporte = id_deporte;
        this.id_entrenador = id_deporte;
        this.id_ejercicio = id_ejercicio;
    }

    public Ejercicio() {

    }

    public void Ejercicio(){

    }

    public int getId_entrenador() {
        return id_entrenador;
    }

    public int getId_ejercicio() {
        return id_ejercicio;
    }

    public void setId_ejercicio(int id_ejercicio) {
        this.id_ejercicio = id_ejercicio;
    }

    public void setId_entrenador(int id_entrenador) {
        this.id_entrenador = id_entrenador;
    }

    public int getId_deporte() {
        return id_deporte;
    }

    public void setId_deporte(int id_deporte) {
        this.id_deporte = id_deporte;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

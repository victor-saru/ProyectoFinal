package com.example.victo.coachmanager.Entidades;

public class Grupo {

    String nombre, categoria;

    public Grupo(String nombre, String categoria){
        this.nombre = nombre;
        this.categoria = categoria;
    }

    public Grupo(){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}

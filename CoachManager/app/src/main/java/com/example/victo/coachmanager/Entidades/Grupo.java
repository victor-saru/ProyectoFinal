package com.example.victo.coachmanager.Entidades;

public class Grupo {

    String nombre, categoria;
    int id_grupo;

    public Grupo(String nombre, String categoria, int id_grupo){
        this.nombre = nombre;
        this.categoria = categoria;
        this.id_grupo = id_grupo;
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

    public int getId_grupo() {
        return id_grupo;
    }

    public void setId_grupo(int id_grupo) {
        this.id_grupo = id_grupo;
    }
}

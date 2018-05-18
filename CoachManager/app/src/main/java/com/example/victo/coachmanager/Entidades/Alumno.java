package com.example.victo.coachmanager.Entidades;

/**
 * Created by Oscar on 14/05/2018.
 */

public class Alumno {

    String nombre, primer_apellido, segundo_apellido, dni, fecha_nacimiento, genero, mano_dom, pie_dom, observaciones;
    int movil, peso, altura;

    /*public Alumno() {
        super();
    }*/

    public Alumno(String nombre, String primer_apellido, String segundo_apellido, String dni, String fecha_nacimiento, String genero, String mano_dom, String pie_dom, String observaciones, int movil, int peso, int altura) {
        this.nombre = nombre;
        this.primer_apellido = primer_apellido;
        this.segundo_apellido = segundo_apellido;
        this.dni = dni;
        this.fecha_nacimiento = fecha_nacimiento;
        this.genero = genero;
        this.mano_dom = mano_dom;
        this.pie_dom = pie_dom;
        this.observaciones = observaciones;
        this.movil = movil;
        this.peso = peso;
        this.altura = altura;
    }

    public Alumno(){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimer_apellido() {
        return primer_apellido;
    }

    public void setPrimer_apellido(String primer_apellido) {
        this.primer_apellido = primer_apellido;
    }

    public String getSegundo_apellido() {
        return segundo_apellido;
    }

    public void setSegundo_apellido(String segundo_apellido) {
        this.segundo_apellido = segundo_apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getMano_dom() {
        return mano_dom;
    }

    public void setMano_dom(String mano_dom) {
        this.mano_dom = mano_dom;
    }

    public String getPie_dom() {
        return pie_dom;
    }

    public void setPie_dom(String pie_dom) {
        this.pie_dom = pie_dom;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getMovil() {
        return movil;
    }

    public void setMovil(int movil) {
        this.movil = movil;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }
}
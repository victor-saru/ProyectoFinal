package com.example.victo.coachmanager.Entidades;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Oscar on 14/05/2018.
 */

public class Alumno implements Parcelable{

    String nombre, primer_apellido, segundo_apellido, dni, fecha_nacimiento, genero, mano_dom, pie_dom, observaciones;
    int movil, peso, altura, id_alumno, id_persona;

    /*public Alumno() {
        super();
    }*/

    public Alumno(int id_alumno, String nombre, String primer_apellido, String segundo_apellido, String dni, String fecha_nacimiento, String genero, String mano_dom, String pie_dom, String observaciones, int movil, int peso, int altura, int id_persona) {
        this.id_alumno = id_alumno;
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
        this.id_persona = id_persona;
    }

    public Alumno(){

    }

    protected Alumno(Parcel in) {
        id_alumno = in.readInt();
        nombre = in.readString();
        primer_apellido = in.readString();
        segundo_apellido = in.readString();
        dni = in.readString();
        fecha_nacimiento = in.readString();
        genero = in.readString();
        mano_dom = in.readString();
        pie_dom = in.readString();
        observaciones = in.readString();
        movil = in.readInt();
        peso = in.readInt();
        altura = in.readInt();
        id_persona = in.readInt();
    }

    public static final Creator<Alumno> CREATOR = new Creator<Alumno>() {
        @Override
        public Alumno createFromParcel(Parcel in) {
            return new Alumno(in);
        }

        @Override
        public Alumno[] newArray(int size) {
            return new Alumno[size];
        }
    };

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

    public int getId_alumno() {
        return id_alumno;
    }

    public void setId_alumno(int id_alumno) {
        this.id_alumno = id_alumno;
    }

    public int getId_persona() {
        return id_persona;
    }

    public void setId_persona(int id_persona) {
        this.id_persona = id_persona;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id_alumno);
        parcel.writeString(nombre);
        parcel.writeString(primer_apellido);
        parcel.writeString(segundo_apellido);
        parcel.writeString(dni);
        parcel.writeString(fecha_nacimiento);
        parcel.writeString(genero);
        parcel.writeString(mano_dom);
        parcel.writeString(pie_dom);
        parcel.writeString(observaciones);
        parcel.writeInt(movil);
        parcel.writeInt(peso);
        parcel.writeInt(altura);
        parcel.writeInt(id_persona);


    }
}
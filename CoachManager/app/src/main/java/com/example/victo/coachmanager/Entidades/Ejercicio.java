package com.example.victo.coachmanager.Entidades;

import android.os.Parcel;
import android.os.Parcelable;

public class Ejercicio implements Parcelable{

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

    protected Ejercicio(Parcel in) {
        id_ejercicio = in.readInt();
        nombre = in.readString();
        descripcion = in.readString();
        id_deporte = in.readInt();
        id_entrenador = in.readInt();
    }

    public static final Creator<Ejercicio> CREATOR = new Creator<Ejercicio>() {
        @Override
        public Ejercicio createFromParcel(Parcel in) {
            return new Ejercicio(in);
        }

        @Override
        public Ejercicio[] newArray(int size) {
            return new Ejercicio[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(id_ejercicio);
        parcel.writeString(nombre);
        parcel.writeString(descripcion);
        parcel.writeInt(id_deporte);
        parcel.writeInt(id_entrenador);


    }
}

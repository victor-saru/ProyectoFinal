package com.example.victo.coachmanager.Entidades;

import android.os.Parcel;
import android.os.Parcelable;

public class Deporte implements Parcelable{

    int id_deporte;
    String nombre;

    public Deporte() {

    }

    protected Deporte(Parcel in) {
        id_deporte = in.readInt();
        nombre = in.readString();
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

    public static final Creator<Deporte> CREATOR = new Creator<Deporte>() {
        @Override
        public Deporte createFromParcel(Parcel in) {
            return new Deporte(in);
        }

        @Override
        public Deporte[] newArray(int size) {
            return new Deporte[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id_deporte);
        parcel.writeString(nombre);
    }
}
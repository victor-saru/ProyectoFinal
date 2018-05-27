package com.example.victo.coachmanager.Entidades;

import android.os.Parcel;
import android.os.Parcelable;

public class Grupo implements Parcelable{

    String nombre, categoria;
    int id_grupo;

    public Grupo(String nombre, String categoria, int id_grupo){
        this.nombre = nombre;
        this.categoria = categoria;
        this.id_grupo = id_grupo;
    }

    public Grupo(){

    }

    protected Grupo(Parcel in) {
        nombre = in.readString();
        categoria = in.readString();
        id_grupo = in.readInt();
    }

    public static final Creator<Grupo> CREATOR = new Creator<Grupo>() {
        @Override
        public Grupo createFromParcel(Parcel in) {
            return new Grupo(in);
        }

        @Override
        public Grupo[] newArray(int size) {
            return new Grupo[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nombre);
        parcel.writeString(categoria);
        parcel.writeInt(id_grupo);
    }
}

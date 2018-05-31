package com.example.victo.coachmanager.Entidades;

import android.os.Parcel;
import android.os.Parcelable;

public class Entrenamiento implements Parcelable{

    int id_entrenamiento;
    String nombre;
    int id_deporte;
    int id_entrenador;

    public Entrenamiento(int id_entrenamiento, String nombre, int id_deporte, int id_entrenador) {
        this.id_entrenamiento = id_entrenamiento;
        this.nombre = nombre;
        this.id_deporte = id_deporte;
        this.id_entrenador = id_entrenador;
    }

    public Entrenamiento() {

    }

    public void Entrenador(){

    }

    protected Entrenamiento(Parcel in) {
        id_entrenamiento = in.readInt();
        nombre = in.readString();
        id_deporte = in.readInt();
        id_entrenador = in.readInt();
    }

    public static final Creator<Entrenamiento> CREATOR = new Creator<Entrenamiento>() {
        @Override
        public Entrenamiento createFromParcel(Parcel in) {
            return new Entrenamiento(in);
        }

        @Override
        public Entrenamiento[] newArray(int size) {
            return new Entrenamiento[size];
        }
    };

    public int getId_entrenamiento() {
        return id_entrenamiento;
    }

    public void setId_entrenamiento(int id_entrenamiento) {
        this.id_entrenamiento = id_entrenamiento;
    }

    public int getId_deporte() {
        return id_deporte;
    }

    public void setId_deporte(int id_deporte) {
        this.id_deporte = id_deporte;
    }

    public int getId_entrenador() {
        return id_entrenador;
    }

    public void setId_entrenador(int id_entrenador) {
        this.id_entrenador = id_entrenador;
    }

    public Entrenamiento (String nombre){
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id_entrenamiento);
        parcel.writeString(nombre);
        parcel.writeInt(id_deporte);
        parcel.writeInt(id_entrenador);
    }
}

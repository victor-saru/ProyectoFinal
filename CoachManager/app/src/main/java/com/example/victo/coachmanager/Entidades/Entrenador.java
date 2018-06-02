package com.example.victo.coachmanager.Entidades;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by VICTOR on 02/06/2018.
 */



public class Entrenador implements Parcelable {


    String nombre, primer_apellido, segundo_apellido, dni, fecha_nacimiento, genero, correo, contrasenya;
    int id_persona, movil, id_entrenador;

    public Entrenador(String nombre, String primer_apellido, String segundo_apellido, String dni, String fecha_nacimiento, String genero, String correo, String contrasenya, int id_persona, int movil, int id_entrenador) {
        this.nombre = nombre;
        this.primer_apellido = primer_apellido;
        this.segundo_apellido = segundo_apellido;
        this.dni = dni;
        this.fecha_nacimiento = fecha_nacimiento;
        this.genero = genero;
        this.correo = correo;
        this.contrasenya = contrasenya;
        this.id_persona = id_persona;
        this.movil = movil;
        this.id_entrenador = id_entrenador;
    }

    public Entrenador() {
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public int getId_persona() {
        return id_persona;
    }

    public void setId_persona(int id_persona) {
        this.id_persona = id_persona;
    }

    public int getMovil() {
        return movil;
    }

    public void setMovil(int movil) {
        this.movil = movil;
    }

    public int getId_entrenador() {
        return id_entrenador;
    }

    public void setId_entrenador(int id_entrenador) {
        this.id_entrenador = id_entrenador;
    }

    protected Entrenador(Parcel in) {
        id_entrenador = in.readInt();
        nombre = in.readString();
        primer_apellido = in.readString();
        segundo_apellido = in.readString();
        dni = in.readString();
        fecha_nacimiento = in.readString();
        correo = in.readString();
        genero = in.readString();
        movil = in.readInt();
        id_persona = in.readInt();
        contrasenya = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id_entrenador);
        parcel.writeString(nombre);
        parcel.writeString(primer_apellido);
        parcel.writeString(segundo_apellido);
        parcel.writeString(dni);
        parcel.writeString(fecha_nacimiento);
        parcel.writeString(correo);
        parcel.writeString(genero);
        parcel.writeInt(movil);
        parcel.writeInt(id_persona);
        parcel.writeString(contrasenya);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Entrenador> CREATOR = new Creator<Entrenador>() {
        @Override
        public Entrenador createFromParcel(Parcel in) {
            return new Entrenador(in);
        }

        @Override
        public Entrenador[] newArray(int size) {
            return new Entrenador[size];
        }
    };
}

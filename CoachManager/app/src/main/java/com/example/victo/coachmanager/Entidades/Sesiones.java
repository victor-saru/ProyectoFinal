package com.example.victo.coachmanager.Entidades;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by VICTOR on 03/06/2018.
 */

public class Sesiones implements Parcelable{

    int id_sesion, id_grupo, id_entrenamiento, id_entrenador;
    String fecha, fecha_hora_inicio, fecha_hora_fin, motivo_cancelacion, valoracion;
    Boolean realizada;


    public Sesiones(int id_sesion, int id_grupo, int id_entrenamiento, String fecha_hora_inicio,String fecha, String fecha_hora_fin, String motivo_cancelacion, String valoracion, Boolean realizada, int id_entrenador) {
        this.id_sesion = id_sesion;
        this.id_grupo = id_grupo;
        this.id_entrenamiento = id_entrenamiento;
        this.fecha = fecha;
        this.fecha_hora_inicio = fecha_hora_inicio;
        this.fecha_hora_fin = fecha_hora_fin;
        this.motivo_cancelacion = motivo_cancelacion;
        this.valoracion = valoracion;
        this.realizada = realizada;
        this.id_entrenador = id_entrenador;
    }

    public Sesiones() {
    }

    protected Sesiones(Parcel in) {
        id_sesion = in.readInt();
        id_grupo = in.readInt();
        id_entrenamiento = in.readInt();
        fecha = in.readString();
        fecha_hora_inicio = in.readString();
        fecha_hora_fin = in.readString();
        motivo_cancelacion = in.readString();
        valoracion = in.readString();
        realizada = Boolean.valueOf(in.readString());
        id_entrenador = in.readInt();
    }

    public static final Creator<Sesiones> CREATOR = new Creator<Sesiones>() {
        @Override
        public Sesiones createFromParcel(Parcel in) {
            return new Sesiones(in);
        }

        @Override
        public Sesiones[] newArray(int size) {
            return new Sesiones[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id_sesion);
        parcel.writeInt(id_grupo);
        parcel.writeInt(id_entrenamiento);
        parcel.writeString(fecha);
        parcel.writeString(fecha_hora_inicio);
        parcel.writeString(fecha_hora_fin);
        parcel.writeString(motivo_cancelacion);
        parcel.writeString(valoracion);
        parcel.writeString(String.valueOf(realizada));
        parcel.writeInt(id_entrenador);
    }

    public int getId_sesion() {
        return id_sesion;
    }

    public void setId_sesion(int id_sesion) {
        this.id_sesion = id_sesion;
    }

    public int getId_grupo() {
        return id_grupo;
    }

    public void setId_grupo(int id_grupo) {
        this.id_grupo = id_grupo;
    }

    public int getId_entrenamiento() {
        return id_entrenamiento;
    }

    public void setId_entrenamiento(int id_entrenamiento) {
        this.id_entrenamiento = id_entrenamiento;
    }

    public String getFecha_hora_inicio() {
        return fecha_hora_inicio;
    }

    public void setFecha_hora_inicio(String fecha_hora_inicio) {
        this.fecha_hora_inicio = fecha_hora_inicio;
    }

    public String getFecha_hora_fin() {
        return fecha_hora_fin;
    }

    public void setFecha_hora_fin(String fecha_hora_fin) {
        this.fecha_hora_fin = fecha_hora_fin;
    }

    public String getMotivo_cancelacion() {
        return motivo_cancelacion;
    }

    public void setMotivo_cancelacion(String motivo_cancelacion) {
        this.motivo_cancelacion = motivo_cancelacion;
    }

    public String getValoracion() {
        return valoracion;
    }

    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }

    public Boolean getRealizada() {
        return realizada;
    }

    public void setRealizada(Boolean realizada) {
        this.realizada = realizada;
    }

    public int getId_entrenador() {
        return id_entrenador;
    }

    public void setId_entrenador(int id_entrenador) {
        this.id_entrenador = id_entrenador;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}

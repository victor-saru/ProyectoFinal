package com.example.victo.coachmanager;

import android.app.Application;

/**
 * Created by victo on 22/05/2018.
 */

public class ObtenerIDs extends Application{

    private String id_persona_Logeada;
    private String id_entrenador;
    private String id_alumno;
    private String ip;

    public String getId_persona_Logeada() {
        return id_persona_Logeada;
    }

    public void setId_persona_Logeada(String id_persona_Logeada) {
        this.id_persona_Logeada = id_persona_Logeada;
    }

    public String getId_entrenador() {
        return id_entrenador;
    }

    public void setId_entrenador(String id_entrenador) {
        this.id_entrenador = id_entrenador;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getId_alumno() {
        return id_alumno;
    }

    public void setId_alumno(String id_alumno) {
        this.id_alumno = id_alumno;
    }
}

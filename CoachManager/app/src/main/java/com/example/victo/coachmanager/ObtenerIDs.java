package com.example.victo.coachmanager;

import android.app.Application;

/**
 * Created by victo on 22/05/2018.
 */

public class ObtenerIDs extends Application{

    private String id_persona;
    private String id_entrenador;
    private String ip;

    public String getId_persona() {
        return id_persona;
    }

    public void setId_persona(String id_persona) {
        this.id_persona = id_persona;
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
}

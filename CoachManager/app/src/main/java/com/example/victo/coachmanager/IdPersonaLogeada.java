package com.example.victo.coachmanager;

import android.app.Application;

/**
 * Created by victo on 22/05/2018.
 */

public class IdPersonaLogeada  extends Application{

    private String id_persona;

    public String getId_persona() {
        return id_persona;
    }

    public void setId_persona(String id_persona) {
        this.id_persona = id_persona;
    }
}

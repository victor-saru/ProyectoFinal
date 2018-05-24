package com.example.victo.coachmanager;

import android.app.Application;

/**
 * Created by victo on 23/05/2018.
 */

public class IPServidor extends Application {

    private String IP;


    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }
}

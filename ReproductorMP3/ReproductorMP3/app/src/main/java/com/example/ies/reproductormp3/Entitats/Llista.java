package com.example.ies.reproductormp3.Entitats;

import java.io.Serializable;

/**
 * Created by victo on 08/03/2018.
 */

public class Llista implements Serializable {

    private Integer id;
    private String nom;

    public Llista(Integer id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Llista() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}

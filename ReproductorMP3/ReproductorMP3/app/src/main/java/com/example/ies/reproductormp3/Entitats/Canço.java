package com.example.ies.reproductormp3.Entitats;

import java.io.Serializable;

/**
 * Created by victo on 08/03/2018.
 */

public class Canço implements Serializable {

    private Integer id_llista;
    private String nom;

    public Canço(Integer id_llista, String nom) {
        this.id_llista = id_llista;
        this.nom = nom;
    }

    public Canço() {
    }

    public Integer getId_llista() {
        return id_llista;
    }

    public void setId_llista(Integer id_llista) {
        this.id_llista = id_llista;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}

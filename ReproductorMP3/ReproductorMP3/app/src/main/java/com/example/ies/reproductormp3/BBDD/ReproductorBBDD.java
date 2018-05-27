package com.example.ies.reproductormp3.BBDD;

/**
 * Created by victo on 08/03/2018.
 */

public class ReproductorBBDD {


    //TAULA LLISTA REPRODUCIO
    public static final String TAULA_LLISTA = "llista";
    //CAMPS LLISTA REPRODUCIO
    public static final String CAMP_ID = "id_llista";
    public static final String CAMP_NOM_LLISTA = "nom_llista";

    //QUERY CREAR TAULA LLISTA
    public static final String CREAR_TAULA_LLISTA = "CREATE TABLE "+ TAULA_LLISTA + " (" + CAMP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CAMP_NOM_LLISTA + " TEXT UNIQUE)";

    //TAULA CANÇONS
    public static final String TAULA_CANÇO = "canço";
    //CAMPS CANÇO
    public static final String CAMP_ID_LLISTA = "id_llista";
    public static final String CAMP_RUTA = "ruta";

    //QUERY CREAR TAULA CANÇO
    public static final String CREAR_TAULA_CANÇO = "CREATE TABLE "+ TAULA_CANÇO + " (" + CAMP_ID_LLISTA + " INTEGER, " + CAMP_RUTA + " TEXT)";

}

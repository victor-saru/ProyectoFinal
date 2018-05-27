package com.example.ies.reproductormp3.BBDD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by victo on 08/03/2018.
 */

public class ConexionSQLiteHelper extends SQLiteOpenHelper{
    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ReproductorBBDD.CREAR_TAULA_CANÇO);
        db.execSQL(ReproductorBBDD.CREAR_TAULA_LLISTA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + ReproductorBBDD.TAULA_LLISTA);
        db.execSQL("DROP TABLE IF EXISTS " + ReproductorBBDD.TAULA_CANÇO);
        onCreate(db);
    }
}

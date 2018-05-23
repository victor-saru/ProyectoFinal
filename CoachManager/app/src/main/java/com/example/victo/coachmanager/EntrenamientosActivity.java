package com.example.victo.coachmanager;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.example.victo.coachmanager.Adapters.AdapterEntrenamiento;
import com.example.victo.coachmanager.Adapters.AdapterGrupo;
import com.example.victo.coachmanager.Entidades.Entrenamiento;
import com.example.victo.coachmanager.Entidades.Grupo;

import java.util.ArrayList;

public class EntrenamientosActivity extends AppCompatActivity {

    ArrayList<Entrenamiento> al_entrenamientos;
    ListView lista_entrenamientos;
    FloatingActionButton flbtnAñadirEntrenamiento;
    AdapterEntrenamiento adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenamientos);

        al_entrenamientos = new ArrayList<Entrenamiento>();
        lista_entrenamientos = (ListView) findViewById(R.id.lv_lista_entrenamientos);
        adapter = new AdapterEntrenamiento(this, al_entrenamientos);
        flbtnAñadirEntrenamiento = (FloatingActionButton) findViewById(R.id.flbtnAñadirEntrenamiento);

    }
}

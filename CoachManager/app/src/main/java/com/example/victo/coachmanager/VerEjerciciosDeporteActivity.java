package com.example.victo.coachmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.victo.coachmanager.Adapters.AdapterEjercicio;
import com.example.victo.coachmanager.Adapters.AdapterGrupo;
import com.example.victo.coachmanager.Entidades.Ejercicio;
import com.example.victo.coachmanager.Entidades.Grupo;

import java.util.ArrayList;

public class VerEjerciciosDeporteActivity extends AppCompatActivity {

    ArrayList<Ejercicio> al_ejercicios;
    ListView lista_ejercicios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_ejercicios_deporte);

        al_ejercicios = new ArrayList<Ejercicio>();
        lista_ejercicios = (ListView) findViewById(R.id.lv_lista_ejercicios);
        AdapterEjercicio adapter = new AdapterEjercicio(this, al_ejercicios);

        Ejercicio e1 = new Ejercicio("Correr", "Uuuuuuf, corre, corre");
        Ejercicio e2 = new Ejercicio("Patear", "Patear el balón y luego tu culo");
        Ejercicio e3 = new Ejercicio("Parar balones", "Párame esta, crrrrrrrack");

        al_ejercicios.add(e1);
        al_ejercicios.add(e2);
        al_ejercicios.add(e3);

        lista_ejercicios.setAdapter(adapter);

        lista_ejercicios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(VerEjerciciosDeporteActivity.this,VerGrupoActivity.class);
                startActivity(intent);

            }
        });
    }
}

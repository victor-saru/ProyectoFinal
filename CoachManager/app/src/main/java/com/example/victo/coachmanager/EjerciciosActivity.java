package com.example.victo.coachmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.victo.coachmanager.Adapters.AdapterAlumno;
import com.example.victo.coachmanager.Adapters.AdapterDeporte;
import com.example.victo.coachmanager.Entidades.Alumno;
import com.example.victo.coachmanager.Entidades.Deporte;

import java.util.ArrayList;

public class EjerciciosActivity extends AppCompatActivity {

    ArrayList<Deporte> al_deportes;
    ListView lista_deportes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios);

        al_deportes = new ArrayList<Deporte>();
        lista_deportes = (ListView) findViewById(R.id.lv_lista_deportes);
        AdapterDeporte adapter = new AdapterDeporte(this, al_deportes);

        Deporte d1 = new Deporte("Fútbol");

        al_deportes.add(d1);

        lista_deportes.setAdapter(adapter);

        lista_deportes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(EjerciciosActivity.this,VerEjerciciosDeporteActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
    }
}

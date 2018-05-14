package com.example.victo.coachmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

public class Alumnos extends AppCompatActivity {

    ArrayList<Alumno> al_alumnos;
    ListView lista_alumnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumnos);

        int id;

        al_alumnos = new ArrayList<Alumno>();
        lista_alumnos = (ListView) findViewById(R.id.lv_lista_alumnos);
        AdapterAlumno adapter = new AdapterAlumno(this, al_alumnos);

        Alumno a1 = new Alumno("Óscar", "Gómez", "Fernández", "49222336J", "22/02/1997", "Indefinido", "Indefinida", "Indefinido", "Es un poco tonto, la verdad.", 667348629, 100000, 100000);

        al_alumnos.add(a1);

        lista_alumnos.setAdapter(adapter);

        lista_alumnos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Alumno a0;
                a0 = (Alumno) lista_alumnos.getItemAtPosition(position);

                Intent intent = new Intent(Alumnos.this,VerAlumno.class);
                startActivity(intent);

            }
        });

    }
}
package com.example.victo.coachmanager;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.victo.coachmanager.Adapters.AdapterAlumno;
import com.example.victo.coachmanager.Entidades.Alumno;

import java.util.ArrayList;

public class AlumnosActivity extends AppCompatActivity {

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
                finish();
                Intent intent = new Intent(AlumnosActivity.this,VerAlumnoActivity.class);
                startActivity(intent);

            }
        });

        FloatingActionButton fabAñadirAlum = (FloatingActionButton) findViewById(R.id.flbtnAñadirAlumno);

        fabAñadirAlum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(AlumnosActivity.this, AnyadirAlumnoActivity.class));
            }
        });

    }
}
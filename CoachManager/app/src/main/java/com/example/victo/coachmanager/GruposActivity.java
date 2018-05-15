package com.example.victo.coachmanager;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.victo.coachmanager.Adapters.AdapterGrupo;
import com.example.victo.coachmanager.Entidades.Grupo;

import java.util.ArrayList;

public class GruposActivity extends AppCompatActivity {

    ArrayList<Grupo> al_grupos;
    ListView lista_grupos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupos);

        int id;

        al_grupos = new ArrayList<Grupo>();
        lista_grupos = (ListView) findViewById(R.id.lv_lista_grupos);
        AdapterGrupo adapter = new AdapterGrupo(this, al_grupos);

        Grupo g1 = new Grupo("Fútbol", "Infantil");

        al_grupos.add(g1);

        lista_grupos.setAdapter(adapter);

        lista_grupos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(GruposActivity.this,VerGrupoActivity.class);
                startActivity(intent);

            }
        });

        FloatingActionButton flbtnAñadirGrupo = (FloatingActionButton) findViewById(R.id.flbtnAñadirGrupo);

        flbtnAñadirGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GruposActivity.this, AnyadirGrupoActivity.class));
            }
        });

    }
}
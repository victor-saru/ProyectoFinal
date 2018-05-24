package com.example.victo.coachmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.victo.coachmanager.Entidades.Ejercicio;

import java.util.ArrayList;

public class VerEntrenamientoActivity extends AppCompatActivity {

    TextView edNombreEntre;
    TextView SpDeporteEntre;
    Button btnEditarEntre;
    ListView lv_ejercicios_deporte;
    ImageView btnVolver;
    ArrayList<Ejercicio> al_ejercicios;

    String resultado;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_entrenamiento);

        edNombreEntre = (TextView) findViewById(R.id.lblNombreEntreVerValor);
        SpDeporteEntre = (TextView) findViewById(R.id.lblDeporteEntreVerValor);
        btnEditarEntre = (Button) findViewById(R.id.btnEditarEntre);
        lv_ejercicios_deporte = (ListView) findViewById(R.id.lv_ejercicios_deporteVer);
        btnVolver = (ImageView) findViewById(R.id.btnVolverVerEntre);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerEntrenamientoActivity.this, EntrenamientosActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }
}

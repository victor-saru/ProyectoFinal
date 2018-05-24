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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.victo.coachmanager.Entidades.Ejercicio;

import java.util.ArrayList;

public class EditarEntrenamientoActivity extends AppCompatActivity {

    EditText edNombreEntre;
    Spinner SpDeporteEntre;
    Button btnGuardarEditEntre;
    ListView lv_ejercicios_deporte;
    ImageView btnVolver;
    ArrayList<Ejercicio> al_ejercicios;

    String resultado;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_entrenamiento);

        edNombreEntre = (EditText) findViewById(R.id.edNombreEntreEditar);
        SpDeporteEntre = (Spinner) findViewById(R.id.SpDeporteEntreEditar);
        btnGuardarEditEntre = (Button) findViewById(R.id.btnGuardarEditEntre);
        lv_ejercicios_deporte = (ListView) findViewById(R.id.lv_ejercicios_deporteEditar);
        btnVolver = (ImageView) findViewById(R.id.btnVolverEditarEntre);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditarEntrenamientoActivity.this, VerEntrenamientoActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }
}

package com.example.victo.coachmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class AnyadirEjercicioActivity extends AppCompatActivity {

    EditText edNombreEjercicio;
    EditText edDescripcionEjercicio;
    Button btnAñadirEjercicio;

    String resultado;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anyadir_ejercicio);

        edNombreEjercicio = (EditText)findViewById(R.id.edNombreEjercicioAñadir);
        edDescripcionEjercicio = (EditText)findViewById(R.id.edDescripcionEjercicioAñadir);
        btnAñadirEjercicio = (Button)findViewById(R.id.btnAñadirEjercicio);

        request = Volley.newRequestQueue(getApplicationContext());

        ImageView btnVolver = (ImageView) findViewById(R.id.btnVolverAñadirEjercicio);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnyadirEjercicioActivity.this, VerEjerciciosDeporteActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }
}

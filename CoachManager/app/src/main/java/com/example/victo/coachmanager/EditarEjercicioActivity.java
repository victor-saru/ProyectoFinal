package com.example.victo.coachmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;

public class EditarEjercicioActivity extends AppCompatActivity {

    EditText edNombreEjercicio;
    EditText edDescripcionEjercicio;

    String resultado;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_ejercicio);

        edNombreEjercicio = (EditText)findViewById(R.id.edNombreEjercicio);
        edDescripcionEjercicio = (EditText)findViewById(R.id.edDescripcionEjercicio);

        ImageView btnVolver = (ImageView) findViewById(R.id.btnVolver);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditarEjercicioActivity.this, VerEjercicioActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }
}
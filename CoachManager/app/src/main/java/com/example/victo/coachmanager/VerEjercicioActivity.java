package com.example.victo.coachmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;

import org.w3c.dom.Text;

public class VerEjercicioActivity extends AppCompatActivity {

    TextView edNombreEjercicio;
    TextView edDescripcionEjercicio;
    Button editarEjercicio;

    String resultado;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_ejercicio);

        edNombreEjercicio = (TextView)findViewById(R.id.lblNombreEjercicioVerValor);
        edDescripcionEjercicio = (TextView)findViewById(R.id.lblDescripcionEjercicioVerValor);
        editarEjercicio = (Button)findViewById(R.id.btnEditarEjercicio);

        ImageView btnVolver = (ImageView) findViewById(R.id.btnVolverVerEjercicio);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerEjercicioActivity.this, VerEjerciciosDeporteActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }
}

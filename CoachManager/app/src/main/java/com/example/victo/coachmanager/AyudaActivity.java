package com.example.victo.coachmanager;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class AyudaActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnAlumnos;
    ImageButton btnGrupos;
    ImageButton btnEjercicios;
    ImageButton btnEntrenamientos;
    ImageView btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);

        btnAlumnos = (ImageButton) findViewById(R.id.btnAlumnosAyuda);
        btnGrupos = (ImageButton) findViewById(R.id.btnGruposAyuda);
        btnEjercicios = (ImageButton) findViewById(R.id.btnEjerciciosAyuda);
        btnEntrenamientos = (ImageButton) findViewById(R.id.btnEntrenamientoAyuda);

        btnVolver = (ImageView) findViewById(R.id.btnVolverAyuda);

        btnAlumnos.setOnClickListener(this);
        btnGrupos.setOnClickListener(this);
        btnEjercicios.setOnClickListener(this);
        btnEntrenamientos.setOnClickListener(this);
        btnVolver.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAlumnosAyuda:{
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=WnOXeBgudn8")));
                break;
            }

            case R.id.btnGruposAyuda:{
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=xv6ejdRIGuM")));
                break;
            }

            case R.id.btnEjerciciosAyuda:{
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=fhqmLY2FDHY")));
                break;
            }

            case R.id.btnEntrenamientoAyuda:{
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=UXD6ACNFjHg")));
                break;
            }

            case R.id.btnVolverAyuda:{
                finish();
                break;
            }
        }
    }
}

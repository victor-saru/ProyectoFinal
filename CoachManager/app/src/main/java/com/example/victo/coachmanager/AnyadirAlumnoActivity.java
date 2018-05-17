package com.example.victo.coachmanager;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class AnyadirAlumnoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anyadir_alumno);

        Button btnAñadirAlumno = (Button) findViewById(R.id.btnAñadirAlumno);

        btnAñadirAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnyadirAlumnoActivity.this, AlumnosActivity.class);
                startActivityForResult(intent,1);
            }
        });

        ImageView btnVolver = (ImageView) findViewById(R.id.btnVolver);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnyadirAlumnoActivity.this, AlumnosActivity.class);
                startActivityForResult(intent,1);
            }
        });

        Spinner SpGeneroAlumno = (Spinner) findViewById(R.id.SpGeneroAlumno);
        String[] GeneroAlumno = {" ", getString(R.string.GeneroMasculino), getString(R.string.GeneroFemenino)};
        SpGeneroAlumno.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, GeneroAlumno));

        Spinner SpManoDom = (Spinner) findViewById(R.id.SpManoDom);
        String[] ManoDom = {" ", getString(R.string.ManoDomIzq), getString(R.string.ManoDomDer)};
        SpManoDom.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ManoDom));

        Spinner SpPieDom = (Spinner) findViewById(R.id.SpPieDom);
        String[] PieDom = {" ", getString(R.string.PieDomIzq), getString(R.string.PieDomDer)};
        SpPieDom.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, PieDom));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
    }
}

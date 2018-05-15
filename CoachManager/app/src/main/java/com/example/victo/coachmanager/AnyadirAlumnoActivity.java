package com.example.victo.coachmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AnyadirAlumnoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anyadir_alumno);

        Button btnAñadirAlumno = (Button) findViewById(R.id.btnAñadirAlumno);

        btnAñadirAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AnyadirAlumnoActivity.this, AlumnosActivity.class));
            }
        });
    }
}

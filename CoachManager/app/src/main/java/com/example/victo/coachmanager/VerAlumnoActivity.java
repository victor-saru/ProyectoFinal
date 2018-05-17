package com.example.victo.coachmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class VerAlumnoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_alumno);

        ImageView btnVolver = (ImageView) findViewById(R.id.btnVolver);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerAlumnoActivity.this, AlumnosActivity.class);
                startActivityForResult(intent,1);
            }
        });


        Button btnEditarAlumno = (Button) findViewById(R.id.btnEditarAlumno);

        btnEditarAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerAlumnoActivity.this, EditarAlumnoActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
    }
}

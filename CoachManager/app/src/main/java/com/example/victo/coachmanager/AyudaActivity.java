package com.example.victo.coachmanager;

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

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAlumnosAyuda:{

                break;
            }

            case R.id.btnGruposAyuda:{

                break;
            }

            case R.id.btnEjerciciosAyuda:{

                break;
            }

            case R.id.btnEntrenamientoAyuda:{

                break;
            }

            case R.id.btnVolverAyuda:{
                finish();
                break;
            }
        }
    }
}

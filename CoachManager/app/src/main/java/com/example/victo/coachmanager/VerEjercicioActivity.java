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
import com.example.victo.coachmanager.Entidades.Alumno;
import com.example.victo.coachmanager.Entidades.Ejercicio;

import org.w3c.dom.Text;

public class VerEjercicioActivity extends AppCompatActivity {

    TextView edNombreEjercicio;
    TextView edDescripcionEjercicio;
    Button editarEjercicio;
    Ejercicio ejercicio;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_ejercicio);

        edNombreEjercicio = (TextView)findViewById(R.id.lblNombreEjercicioVerValor);
        edDescripcionEjercicio = (TextView)findViewById(R.id.lblDescripcionEjercicioVerValor);
        editarEjercicio = (Button)findViewById(R.id.btnEditarEjercicio);

        ejercicio = new Ejercicio();
        ejercicio = (Ejercicio) getIntent().getParcelableExtra("ejercicio");

        edNombreEjercicio.setText(ejercicio.getNombre());
        edDescripcionEjercicio.setText(ejercicio.getDescripcion());


        ImageView btnVolver = (ImageView) findViewById(R.id.btnVolverVerEjercicio);

        editarEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerEjercicioActivity.this,EditarEjercicioActivity.class);
                intent.putExtra("ejercicio", ejercicio);
                startActivityForResult(intent,1);
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
    }

}

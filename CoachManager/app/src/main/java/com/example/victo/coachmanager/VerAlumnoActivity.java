package com.example.victo.coachmanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.victo.coachmanager.Entidades.Alumno;

public class VerAlumnoActivity extends AppCompatActivity {

    Alumno alumno;
    TextView edNombreAlumno;
    TextView edPrimerApellidoAlumno;
    TextView edSegundoApellidoAlumno;
    TextView spGeneroAlumno;
    TextView edFechaNacimientoAlumno;
    TextView edDNIAlumno;
    TextView edMovilAlumno;
    TextView lblPesoAlumnoValor;
    TextView lblAlturaAlumnoValor;
    TextView lblManoDomAlumnoValor;
    TextView lblPieDomAlumnoValor;
    TextView lblObservacionesAlumnoValor;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_alumno);

        ImageView btnVolver = (ImageView) findViewById(R.id.btnVolver);

        edNombreAlumno = (TextView) findViewById(R.id.edNombreGrupo);
        edPrimerApellidoAlumno = (TextView) findViewById(R.id.edPrimerApellidoAlumno);
        edSegundoApellidoAlumno = (TextView) findViewById(R.id.edSegundoApellidoAlumno);
        spGeneroAlumno = (TextView) findViewById(R.id.SpGeneroAlumno);
        edFechaNacimientoAlumno = (TextView) findViewById(R.id.edFechaNacimientoAlumno);
        edDNIAlumno = (TextView) findViewById(R.id.edDNIAlumno);
        edMovilAlumno = (TextView) findViewById(R.id.edMovilAlumno);
        lblPesoAlumnoValor = (TextView) findViewById(R.id.lblPesoAlumnoValor);
        lblAlturaAlumnoValor = (TextView) findViewById(R.id.lblAlturaAlumnoValor);
        lblManoDomAlumnoValor = (TextView) findViewById(R.id.lblManoDomAlumnoValor);
        lblPieDomAlumnoValor = (TextView) findViewById(R.id.lblPieDomAlumnoValor);
        lblObservacionesAlumnoValor = (TextView) findViewById(R.id.lblObservacionesAlumnoValor);

        alumno = (Alumno) getIntent().getParcelableExtra("alumno");

        System.out.println("Nombre: "+alumno.getNombre());
        System.out.println("Primer: "+alumno.getPrimer_apellido());
        System.out.println("Segundo: "+alumno.getSegundo_apellido());
        System.out.println("DNI: "+alumno.getDni());
        System.out.println("Fecha: "+alumno.getFecha_nacimiento());
        System.out.println("mano: "+alumno.getMano_dom());
        System.out.println("genero: "+alumno.getGenero());
        System.out.println("pie: "+alumno.getPie_dom());
        System.out.println("observ: "+alumno.getObservaciones());
        System.out.println("movil: "+alumno.getMovil());
        System.out.println("peso: "+alumno.getPeso());
        System.out.println("altura: "+alumno.getAltura());

        edNombreAlumno.setText(alumno.getNombre());
        edPrimerApellidoAlumno.setText(alumno.getPrimer_apellido());
        edSegundoApellidoAlumno.setText(alumno.getSegundo_apellido());
        edDNIAlumno.setText(alumno.getDni());
        edFechaNacimientoAlumno.setText(alumno.getFecha_nacimiento());
        lblManoDomAlumnoValor.setText(alumno.getMano_dom());
        spGeneroAlumno.setText(alumno.getGenero());
        lblPieDomAlumnoValor.setText(alumno.getPie_dom());
        lblObservacionesAlumnoValor.setText(alumno.getObservaciones());
        edMovilAlumno.setText(String.valueOf(alumno.getMovil()));
        lblPesoAlumnoValor.setText(String.valueOf(alumno.getPeso()));
        lblAlturaAlumnoValor.setText(String.valueOf(alumno.getAltura()));













        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
        //finish();
    }
}

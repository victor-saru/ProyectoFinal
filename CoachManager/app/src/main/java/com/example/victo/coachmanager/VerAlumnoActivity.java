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

        ImageView btnVolver = (ImageView) findViewById(R.id.btnVolverVerAlumno);

        edNombreAlumno = (TextView) findViewById(R.id.lblNombreAlumnoVerValor);
        edPrimerApellidoAlumno = (TextView) findViewById(R.id.lblPrimApeAlumnoVerValor);
        edSegundoApellidoAlumno = (TextView) findViewById(R.id.lblSegApeAlumnoVerValor);
        spGeneroAlumno = (TextView) findViewById(R.id.lblGeneroAlumnoVerValor);
        edFechaNacimientoAlumno = (TextView) findViewById(R.id.lblFechaAlumnoVerValor);
        edDNIAlumno = (TextView) findViewById(R.id.lblDNIAlumnoVerValor);
        edMovilAlumno = (TextView) findViewById(R.id.lblMovilAlumnoVerValor);
        lblPesoAlumnoValor = (TextView) findViewById(R.id.lblPesoAlumnoVerValor);
        lblAlturaAlumnoValor = (TextView) findViewById(R.id.lblAlturaAlumnoVerValor);
        lblManoDomAlumnoValor = (TextView) findViewById(R.id.lblManoDomAlumnoVerValor);
        lblPieDomAlumnoValor = (TextView) findViewById(R.id.lblPieDomAlumnoVerValor);
        lblObservacionesAlumnoValor = (TextView) findViewById(R.id.lblObservAlumnoVerValor);
        lblObservacionesAlumnoValor.setEnabled(false);

        alumno = (Alumno) getIntent().getParcelableExtra("alumno");


        edNombreAlumno.setText(alumno.getNombre());
        edPrimerApellidoAlumno.setText(alumno.getPrimer_apellido());
        edSegundoApellidoAlumno.setText(alumno.getSegundo_apellido());
        edDNIAlumno.setText(alumno.getDni());
        edFechaNacimientoAlumno.setText(alumno.getFecha_nacimiento());

        if(alumno.getMano_dom().equals("Derecha") || alumno.getMano_dom().equals("Dreta") || alumno.getMano_dom().equals("Right"))
            lblManoDomAlumnoValor.setText(getString(R.string.ManoDomDer));
        else if (alumno.getGenero().equals(" "))
            lblManoDomAlumnoValor.setText(" ");
        else
            lblManoDomAlumnoValor.setText(getString(R.string.ManoDomIzq));


        if(alumno.getGenero().equals("Masculino") || alumno.getGenero().equals("Masculí") || alumno.getGenero().equals("Male"))
            spGeneroAlumno.setText(getString(R.string.GeneroMasculino));
        else if (alumno.getGenero().equals(" "))
            spGeneroAlumno.setText(" ");
        else
            spGeneroAlumno.setText(getString(R.string.GeneroFemenino));

        if(alumno.getPie_dom().equals("Derecho") || alumno.getPie_dom().equals("Dret") || alumno.getPie_dom().equals("Right"))
            lblPieDomAlumnoValor.setText(getString(R.string.PieDomDer));
        else if (alumno.getPie_dom().equals(" "))
            lblPieDomAlumnoValor.setText(" ");
        else
            lblPieDomAlumnoValor.setText(getString(R.string.PieDomIzq));


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
                intent.putExtra("alumno", alumno);
                startActivityForResult(intent,1);
            }
        });
    }




    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
    }
}

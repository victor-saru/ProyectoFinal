package com.example.victo.coachmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.victo.coachmanager.Adapters.AdapterEntrenamiento;
import com.example.victo.coachmanager.Entidades.Entrenamiento;
import com.example.victo.coachmanager.Entidades.Sesiones;

import java.util.ArrayList;

public class EditarSesionActivity extends AppCompatActivity {



    EditText edFechaSesion;
    EditText edHoraInicioSesion;
    EditText edHoraFinSesion;
    EditText edValoracionSesion;
    EditText edMotivoSesion;
    Spinner spGruposSesion;
    CheckBox chbRealizadaSesion;
    ListView lv_entrenamientos_sesion;
    String resultado;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    ImageView btnVolver;
    Sesiones sesion;
    ArrayList<Entrenamiento> al_entrenamientos;
    AdapterEntrenamiento adapterEntrenamiento;
    Button btnEditarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_sesion);

        sesion = (Sesiones) getIntent().getParcelableExtra("sesion");


        edFechaSesion = (EditText) findViewById(R.id.edFechaInicioSesionEditarSesion);
        edHoraInicioSesion = (EditText) findViewById(R.id.edInicioSesionEditarSesion);
        edHoraFinSesion = (EditText) findViewById(R.id.edHoraFinEditarSesion);
        edValoracionSesion = (EditText) findViewById(R.id.edValoracionSesionEditarSesion);
        edMotivoSesion = (EditText) findViewById(R.id.edMotivoSesionEditarSesion);
        spGruposSesion = (Spinner) findViewById(R.id.SpGruposSesionEditarSesion);
        chbRealizadaSesion  = (CheckBox) findViewById(R.id.chbRealizadaSesionEditarSesion);
        lv_entrenamientos_sesion  = (ListView) findViewById(R.id.lv_entrenamientos_sesionEditarSesion);
        btnVolver = (ImageView) findViewById(R.id.btnVolverAñadirSesionEditarSesion);
        btnEditarSesion = (Button) findViewById(R.id.btnEditarSesionEditarSesion);


        edFechaSesion.setText(sesion.getFecha_hora_inicio());
        edHoraInicioSesion.setText(sesion.getFecha_hora_inicio());
        edHoraFinSesion.setText(sesion.getFecha_hora_fin());

        edValoracionSesion.setText(sesion.getValoracion());
        edMotivoSesion.setText(sesion.getMotivo_cancelacion());
        chbRealizadaSesion.setChecked(sesion.getRealizada());

        String[] GrupoSesion = new String[SesionesActivity.al_grupos.size()];

        for(int i = 0; i < SesionesActivity.al_grupos.size(); i++){
            GrupoSesion[i] = SesionesActivity.al_grupos.get(i).getNombre();

        }

        spGruposSesion.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, GrupoSesion));



        al_entrenamientos = new ArrayList<>();
    }
}

package com.example.victo.coachmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.victo.coachmanager.Entidades.Alumno;
import com.example.victo.coachmanager.Entidades.Grupo;

import java.util.ArrayList;

public class VerGrupoActivity extends AppCompatActivity {

    TextView edNombreGrupo;
    TextView edCategoriaGrupo;
    Button btnEditarGrupo;
    ListView lv_alumnos_grupo;
    ArrayList<Alumno> al_alumnos;
    ImageView btnVolver;
    String resultado;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    Grupo grupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_grupo);

        edNombreGrupo = (TextView) findViewById(R.id.lblNombreGrupoVerValor);
        edCategoriaGrupo = (TextView) findViewById(R.id.lblCategoriaGrupoVerValor);
        btnEditarGrupo = (Button) findViewById(R.id.btnEditarGrupo);
        lv_alumnos_grupo = (ListView)findViewById(R.id.lv_alumnos_grupoVer);
        btnVolver = (ImageView) findViewById(R.id.btnVolverVerGrupo);

        grupo = (Grupo) getIntent().getParcelableExtra("grupo");


        edNombreGrupo.setText(grupo.getNombre());
        edCategoriaGrupo.setText(grupo.getCategoria());


        request = Volley.newRequestQueue(getApplicationContext());

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnEditarGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerGrupoActivity.this, EditarGrupoActivity.class);
                intent.putExtra("grupo", grupo);
                startActivityForResult(intent,1);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
    }
}

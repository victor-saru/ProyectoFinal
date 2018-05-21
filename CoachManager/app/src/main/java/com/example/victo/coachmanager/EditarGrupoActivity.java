package com.example.victo.coachmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;

public class EditarGrupoActivity extends AppCompatActivity {

    EditText edNombreGrupo;
    EditText edCategoriaGrupo;
    Button btnGuardarCambios;
    ListView lv_alumnos_grupo;

    String resultado;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_grupo);

        edNombreGrupo = (EditText) findViewById(R.id.edNombreGrupo);
        edCategoriaGrupo = (EditText) findViewById(R.id.edCategoriaGrupo);
        btnGuardarCambios = (Button) findViewById(R.id.btnGuardarEditGrupo);
        lv_alumnos_grupo = (ListView)findViewById(R.id.lv_lista_alumnos);

        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditarGrupoActivity.this, VerGrupoActivity.class);
                startActivityForResult(intent,1);
            }
        });

        ImageView btnVolver = (ImageView) findViewById(R.id.btnVolver);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditarGrupoActivity.this, VerGrupoActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
    }
}

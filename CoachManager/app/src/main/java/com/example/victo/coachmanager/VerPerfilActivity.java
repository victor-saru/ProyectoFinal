package com.example.victo.coachmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.victo.coachmanager.Entidades.Alumno;
import com.example.victo.coachmanager.Entidades.Entrenador;
import com.example.victo.coachmanager.Entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VerPerfilActivity extends AppCompatActivity implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener{

    TextView edCorreoRegistro;
    TextView edPasswordRegistro;
    TextView edNombreRegistro;
    TextView edPrimerApellidoRegistro;
    TextView edSegundoApellidoRegistro;
    TextView edDNIRegistro;
    TextView edMovilRegistro;
    TextView edFechaRegistro;
    Button btnRegistrarse;
    TextView spinnerGeneroRegistro;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue ConsultaRequest;
    JsonObjectRequest jsonConsultaRequest;
    String resultado;
    String genero;
    Entrenador a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);


        edCorreoRegistro = (TextView) findViewById(R.id.edCorreoRegistroPerfil);
        edPasswordRegistro = (TextView) findViewById(R.id.edPasswordRegistroPerfil);
        edNombreRegistro = (TextView) findViewById(R.id.edNombreRegistroPerfil);
        edPrimerApellidoRegistro = (TextView) findViewById(R.id.edPrimerApellidoRegistroPerfil);
        edSegundoApellidoRegistro = (TextView) findViewById(R.id.edSegundoApellidoRegistroPerfil);
        edDNIRegistro = (TextView) findViewById(R.id.edDNIRegistroPerfil);
        edMovilRegistro = (TextView) findViewById(R.id.edMovilRegistroPerfil);
        edFechaRegistro = (TextView) findViewById(R.id.edFechaRegistroPerfil);
        edFechaRegistro.setOnClickListener(this);
        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarPerfil);
        spinnerGeneroRegistro = (TextView) findViewById(R.id.spinnerGeneroRegistroPerfil);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarPerfil);
        toolbar.setTitle(getString(R.string.Perfil));
        setSupportActionBar(toolbar);

        cargarWebServer();

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerPerfilActivity.this,EditarPerfilActivity.class);
                intent.putExtra("entrenador", a);
                startActivityForResult(intent,1);
            }
        });
    }

    private void cargarWebServer() {


        String url = "http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_Entrenador.php?id_persona="+((ObtenerIDs) this.getApplication()).getId_persona_Logeada();
        System.out.println(url);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onResponse(JSONObject response) {

        JSONArray jsonEntrenador = response.optJSONArray("entrenador");

        JSONObject jsonObjectEntrenador=null;

        if(jsonEntrenador != null){
            try {
                jsonObjectEntrenador = jsonEntrenador.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            a = new Entrenador();

            a.setId_entrenador(jsonObjectEntrenador.optInt("id_entrenador"));
            a.setNombre(jsonObjectEntrenador.optString("nombre"));
            a.setPrimer_apellido(jsonObjectEntrenador.optString("primer_apellido"));
            a.setSegundo_apellido(jsonObjectEntrenador.optString("segundo_apellido"));
            a.setDni(jsonObjectEntrenador.optString("dni"));
            a.setFecha_nacimiento(jsonObjectEntrenador.optString("fecha_nacimiento"));
            a.setGenero(jsonObjectEntrenador.optString("genero"));
            a.setContrasenya(jsonObjectEntrenador.optString("contrasenya"));
            a.setCorreo(jsonObjectEntrenador.optString("correo"));
            a.setMovil(jsonObjectEntrenador.optInt("movil"));
            a.setId_persona(jsonObjectEntrenador.optInt("id_persona"));

            edNombreRegistro.setText(a.getNombre());
            edPrimerApellidoRegistro.setText(a.getPrimer_apellido());
            edSegundoApellidoRegistro.setText(a.getSegundo_apellido());
            edCorreoRegistro.setText(a.getCorreo());
            edPasswordRegistro.setText("");
            spinnerGeneroRegistro.setText(a.getGenero());
            edMovilRegistro.setText(String.valueOf(a.getMovil()));
            edDNIRegistro.setText(a.getDni());
            edFechaRegistro.setText(a.getFecha_nacimiento());
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), getString(R.string.errorConexionBD), Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());

    }


}



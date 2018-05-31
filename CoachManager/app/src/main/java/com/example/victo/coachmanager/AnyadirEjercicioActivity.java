package com.example.victo.coachmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.victo.coachmanager.Entidades.Deporte;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AnyadirEjercicioActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    EditText edNombreEjercicio;
    EditText edDescripcionEjercicio;
    Button btnAñadirEjercicio;
    Deporte deporte;

    String resultado;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anyadir_ejercicio);

        edNombreEjercicio = (EditText)findViewById(R.id.edNombreEjercicioAñadir);
        edDescripcionEjercicio = (EditText)findViewById(R.id.edDescripcionEjercicioAñadir);
        btnAñadirEjercicio = (Button)findViewById(R.id.btnAñadirEjercicio);
        deporte = new Deporte();
        request = Volley.newRequestQueue(getApplicationContext());

        ImageView btnVolver = (ImageView) findViewById(R.id.btnVolverAñadirEjercicio);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAñadirEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarWebService();
            }
        });
    }

    private void cargarWebService() {

        String id_entrenador = ((ObtenerIDs) this.getApplication()).getId_entrenador();
        deporte = (Deporte) getIntent().getParcelableExtra("deporte");


        String url = "http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_InsertEjercicio.php?nombre="+edNombreEjercicio.getText().toString()
                + "&descripcion="+edDescripcionEjercicio.getText()
                + "&id_deporte="+deporte.getId_deporte()
                + "&id_entrenador="+id_entrenador;

        System.out.println(url);

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {

        JSONArray json=response.optJSONArray("ejercicio");
        JSONObject jsonObject=null;

        try {
            jsonObject = json.getJSONObject(0);
            resultado = (jsonObject.optString("resultado"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(resultado.equals("Null")){
            Toast.makeText(getApplicationContext(), getString(R.string.errorRellCampsObl), Toast.LENGTH_SHORT).show();
        }

        else if(resultado.equals("NombreReperido")){
            Toast.makeText(getApplicationContext(), "Ya existe un ejercicio con ese nombre", Toast.LENGTH_SHORT).show();//Óscar
        }

        else{

            Toast.makeText(getApplicationContext(), getString(R.string.RegistradoExito), Toast.LENGTH_SHORT).show();
            finish();

        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getApplicationContext(), getString(R.string.RegistradoExito), Toast.LENGTH_SHORT).show();
        finish();

    }


}

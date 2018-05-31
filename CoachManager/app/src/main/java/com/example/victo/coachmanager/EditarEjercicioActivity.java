package com.example.victo.coachmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.example.victo.coachmanager.Entidades.Ejercicio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EditarEjercicioActivity extends AppCompatActivity implements  Response.Listener<JSONObject>, Response.ErrorListener{

    EditText edNombreEjercicio;
    EditText edDescripcionEjercicio;
    Button btnGuardarEditEjercicio;
    Ejercicio ejercicio;

    String resultado;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_ejercicio);

        edNombreEjercicio = (EditText)findViewById(R.id.edNombreEjercicioEditar);
        edDescripcionEjercicio = (EditText)findViewById(R.id.edDescripcionEjercicioEditar);
        btnGuardarEditEjercicio = (Button)findViewById(R.id.btnGuardarEditEjercicio);
        ImageView btnVolver = (ImageView) findViewById(R.id.btnVolverEditarEjercicio);

        ejercicio = new Ejercicio();
        ejercicio = (Ejercicio) getIntent().getParcelableExtra("ejercicio");

        edNombreEjercicio.setText(ejercicio.getNombre());
        edDescripcionEjercicio.setText(ejercicio.getDescripcion());

        request = Volley.newRequestQueue(getApplicationContext());

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnGuardarEditEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarWebService();
            }
        });
    }

    private void cargarWebService() {

        String url = "http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_UpdateEjercicio.php?id_ejercicio="+String.valueOf(ejercicio.getId_ejercicio())
                +"&nombre="+edNombreEjercicio.getText().toString()
                +"&descripcion="+edDescripcionEjercicio.getText().toString()
                +"&id_deporte="+String.valueOf(ejercicio.getId_deporte())
                +"&id_entrenador="+String.valueOf(ejercicio.getId_entrenador());

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

        else{
            Toast.makeText(getApplicationContext(), getString(R.string.GuardarCambios), Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getApplicationContext(), getString(R.string.errorConexionBD), Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());

    }


}

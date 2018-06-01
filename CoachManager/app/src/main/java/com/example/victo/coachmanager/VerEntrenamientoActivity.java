package com.example.victo.coachmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.victo.coachmanager.Entidades.Deporte;
import com.example.victo.coachmanager.Entidades.Ejercicio;
import com.example.victo.coachmanager.Entidades.Entrenamiento;
import com.example.victo.coachmanager.Entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VerEntrenamientoActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    TextView edNombreEntre;
    TextView SpDeporteEntre;
    Button btnEditarEntre;
    ListView lv_ejercicios_deporte;
    ImageView btnVolver;
    ArrayList<Ejercicio> al_ejercicios;
    Entrenamiento entrenamiento;
    Deporte d;

    String resultado;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_entrenamiento);

        edNombreEntre = (TextView) findViewById(R.id.lblNombreEntreVerValor);
        SpDeporteEntre = (TextView) findViewById(R.id.lblDeporteEntreVerValor);
        btnEditarEntre = (Button) findViewById(R.id.btnEditarEntre);
        lv_ejercicios_deporte = (ListView) findViewById(R.id.lv_ejercicios_deporteVer);
        btnVolver = (ImageView) findViewById(R.id.btnVolverVerEntre);

        entrenamiento = (Entrenamiento) getIntent().getParcelableExtra("entrenamiento");

        edNombreEntre.setText(entrenamiento.getNombre());

        cargarWebServiceDeporte();
        cargarWebService();




        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerEntrenamientoActivity.this, EntrenamientosActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }

    private void cargarWebService() {

        String id_entrenador = ((ObtenerIDs) this.getApplication()).getId_entrenador();

        String url="http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_EjerEntre.php?id_entrenamiento="+entrenamiento.getId_entrenamiento();
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void cargarWebServiceDeporte() {
        String url="http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_Deporte.php?id_deporte="+String.valueOf(entrenamiento.getId_deporte());
        System.out.println(url);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {

        JSONArray jsonDeporte = response.optJSONArray("deporte");
        JSONObject jsonObjectDeporte = null;

        if(jsonDeporte != null){
            try {
                jsonObjectDeporte = jsonDeporte.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            d = new Deporte();

            d.setId_deporte(jsonObjectDeporte.optInt("id_deporte"));
            d.setNombre(jsonObjectDeporte.optString("nombre"));

            SpDeporteEntre.setText(d.getNombre());
            System.out.println(d.getNombre());
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }


}

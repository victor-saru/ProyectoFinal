package com.example.victo.coachmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.victo.coachmanager.Adapters.AdapterAlumno;
import com.example.victo.coachmanager.Adapters.AdapterDeporte;
import com.example.victo.coachmanager.Adapters.AdapterEjercicio;
import com.example.victo.coachmanager.Entidades.Alumno;
import com.example.victo.coachmanager.Entidades.Deporte;
import com.example.victo.coachmanager.Entidades.VolleySingleton;

import org.json.JSONObject;

import java.util.ArrayList;

public class EjerciciosActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    ArrayList<Deporte> al_deportes;
    ListView lista_deportes;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    AdapterEjercicio adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios);

        al_deportes = new ArrayList<Deporte>();
        lista_deportes = (ListView) findViewById(R.id.lv_lista_deportes);
        AdapterDeporte adapter = new AdapterDeporte(this, al_deportes);

        cargarWebService();

        Deporte d1 = new Deporte("Fútbol");

        al_deportes.add(d1);

        lista_deportes.setAdapter(adapter);

        lista_deportes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(EjerciciosActivity.this,VerEjerciciosDeporteActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }

    private void cargarWebService() {
        String url="http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_Deportes.php";


        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

    }
}

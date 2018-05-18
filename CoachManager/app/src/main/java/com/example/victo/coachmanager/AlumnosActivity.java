package com.example.victo.coachmanager;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.victo.coachmanager.Adapters.AdapterAlumno;
import com.example.victo.coachmanager.Entidades.Alumno;
import com.example.victo.coachmanager.Entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AlumnosActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    ArrayList<Alumno> al_alumnos;
    ListView lista_alumnos;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    AdapterAlumno adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumnos);

        int id;

        al_alumnos = new ArrayList<Alumno>();
        lista_alumnos = (ListView) findViewById(R.id.lv_lista_alumnos);


        cargarWebService();

        lista_alumnos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AlumnosActivity.this,VerAlumnoActivity.class);
                startActivityForResult(intent,1);
                //startActivity(intent);

            }
        });

        FloatingActionButton fabAñadirAlum = (FloatingActionButton) findViewById(R.id.flbtnAñadirAlumno);

        fabAñadirAlum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlumnosActivity.this, AnyadirAlumnoActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
    }

    private void cargarWebService(){

        String url="http://192.168.1.45/CoachManagerPHP/CoachManager_Alumnos.php";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {

        System.out.println("HOLAAAAAAAA");

        JSONArray json = response.optJSONArray("alumnos");

        try {

            for(int i = 0; i < json.length(); i++){
                Alumno a = new Alumno();
                JSONObject jsonObject = null;
                jsonObject=json.getJSONObject(i);

                a.setNombre(jsonObject.optString("nombre"));
                a.setPrimer_apellido(jsonObject.optString("primer_apellido"));
                a.setSegundo_apellido(jsonObject.optString("segundo_apellido"));
                a.setDni(jsonObject.optString("dni"));
                a.setFecha_nacimiento(jsonObject.optString("fecha_nacimiento"));
                a.setGenero(jsonObject.optString("genero"));
                a.setMano_dom(jsonObject.optString("mano_dom"));
                a.setPie_dom(jsonObject.optString("pie_dom"));
                a.setObservaciones(jsonObject.optString("observaciones"));
                a.setMovil(jsonObject.optInt("movil"));
                a.setPeso(jsonObject.optInt("peso"));
                a.setAltura(jsonObject.optInt("altura"));

                al_alumnos.add(a);


            }

            adapter = new AdapterAlumno(this, al_alumnos);
            lista_alumnos.setAdapter(adapter);

        }catch(JSONException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "No se ha podido conectar con la base de datos", Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());

    }


}
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
import com.example.victo.coachmanager.Adapters.AdapterGrupo;
import com.example.victo.coachmanager.Entidades.Alumno;
import com.example.victo.coachmanager.Entidades.Grupo;
import com.example.victo.coachmanager.Entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GruposActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    ArrayList<Grupo> al_grupos;
    ListView lista_grupos;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    AdapterGrupo adapter;
    private int id_entrenador;
    private int id_persona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupos);

        Bundle objecteEnviat = getIntent().getExtras();


        if(objecteEnviat != null){
            Integer id_persona = (Integer) objecteEnviat.getSerializable("i");
        }

        al_grupos = new ArrayList<Grupo>();
        lista_grupos = (ListView) findViewById(R.id.lv_lista_grupos);
        adapter = new AdapterGrupo(this, al_grupos);

        cargarWebService();



        lista_grupos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(GruposActivity.this,VerGrupoActivity.class);
                startActivity(intent);

            }
        });

        FloatingActionButton flbtnAñadirGrupo = (FloatingActionButton) findViewById(R.id.flbtnAñadirGrupo);

        flbtnAñadirGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GruposActivity.this, AnyadirGrupoActivity.class));
            }
        });

    }

    private void cargarWebService() {

        String url="http://192.168.1.45/CoachManagerPHP/CoachManager_Grupos.php";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);


    }

    @Override
    public void onResponse(JSONObject response) {

        JSONArray json = response.optJSONArray("grupos");

        try {

            for (int i = 0; i < json.length(); i++) {
                Grupo g = new Grupo();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);

                g.setNombre(jsonObject.optString("nombre"));
                g.setCategoria(jsonObject.optString("categoria"));


                al_grupos.add(g);


            }


            adapter = new AdapterGrupo(this, al_grupos);
            lista_grupos.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), getString(R.string.errorConexionBD), Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());
    }
}
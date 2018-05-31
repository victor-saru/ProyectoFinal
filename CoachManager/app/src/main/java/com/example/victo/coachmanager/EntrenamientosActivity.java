package com.example.victo.coachmanager;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.victo.coachmanager.Adapters.AdapterEntrenamiento;
import com.example.victo.coachmanager.Adapters.AdapterGrupo;
import com.example.victo.coachmanager.Entidades.Entrenamiento;
import com.example.victo.coachmanager.Entidades.Grupo;
import com.example.victo.coachmanager.Entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EntrenamientosActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    ArrayList<Entrenamiento> al_entrenamientos;
    ListView lista_entrenamientos;
    FloatingActionButton flbtnAñadirEntrenamiento;
    AdapterEntrenamiento adapter;
    private ArrayAdapter<Entrenamiento> adapterEntrenamientos;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenamientos);

        al_entrenamientos = new ArrayList<Entrenamiento>();
        lista_entrenamientos = (ListView) findViewById(R.id.lv_lista_entrenamientos);
        adapter = new AdapterEntrenamiento(this, al_entrenamientos);
        flbtnAñadirEntrenamiento = (FloatingActionButton) findViewById(R.id.flbtnAñadirEntrenamiento);

        cargarWebService();

    }

    private void cargarWebService() {

        String id_entrenador = ((ObtenerIDs) this.getApplication()).getId_entrenador();

        String url="http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_Entrenamientos.php?id_entrenador="+id_entrenador;
        System.out.println(url);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {

        JSONArray json = response.optJSONArray("entrenamientos");

        JSONObject jsonObject2 = null;

        try {
            jsonObject2 = json.getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String resultado = (jsonObject2.optString("resultado"));

        if(resultado.equals("Eliminado")){
            Toast.makeText(getApplicationContext(), getString(R.string.GrupoEliminado), Toast.LENGTH_SHORT).show();
            adapter = new AdapterEntrenamiento(this, al_entrenamientos);
            lista_entrenamientos.setAdapter(adapter);
        }

        else if (!resultado.equals("Null")) {
            al_entrenamientos.removeAll(al_entrenamientos);

            try {

                for (int i = 0; i < json.length(); i++) {

                    Entrenamiento e = new Entrenamiento();
                    JSONObject jsonObject = null;
                    jsonObject = json.getJSONObject(i);

                    e.setId_entrenamiento(jsonObject.optInt("id_entrenamiento"));
                    e.setNombre(jsonObject.optString("nombre"));
                    e.setId_deporte(jsonObject.optInt("id_deporte"));
                    e.setId_entrenador(jsonObject.optInt("id_entrenador"));


                    al_entrenamientos.add(e);

                }



                adapter = new AdapterEntrenamiento(this, al_entrenamientos);
                lista_entrenamientos.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), getString(R.string.errorConexionBD), Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());
    }


}

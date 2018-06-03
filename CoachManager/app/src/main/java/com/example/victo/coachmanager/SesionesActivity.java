package com.example.victo.coachmanager;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.victo.coachmanager.Adapters.AdapterGrupo;
import com.example.victo.coachmanager.Adapters.AdapterSesion;
import com.example.victo.coachmanager.Entidades.Grupo;
import com.example.victo.coachmanager.Entidades.Sesiones;
import com.example.victo.coachmanager.Entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SesionesActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    ArrayList<Sesiones> al_sesiones;
    public static ArrayList<Grupo> al_grupos;
    ListView lista_sesiones;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    AdapterSesion adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesiones);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSesiones);
        toolbar.setTitle(getString(R.string.Sesiones));
        setSupportActionBar(toolbar);

        lista_sesiones = (ListView) findViewById(R.id.lv_lista_sesiones);
        al_sesiones = new ArrayList<>();
        al_grupos = new ArrayList<>();

        FloatingActionButton flbtnAñadirSesion = (FloatingActionButton) findViewById(R.id.flbtnAñadirSesion);

        cargarWebService();
        cargarWebServiceGrupos();
    }

    private void cargarWebServiceGrupos() {
        String url="http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_Grupos.php?id_entrenador="+((ObtenerIDs) this.getApplication()).getId_entrenador();
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void cargarWebService() {

        String url="http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_Sesiones.php?id_entrenador="+((ObtenerIDs) this.getApplication()).getId_entrenador();
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {

        JSONArray jsonSesiones = response.optJSONArray("sesiones");
        JSONObject jsonObjectSesiones = null;

        JSONArray jsonGrupos = response.optJSONArray("grupos");
        JSONObject jsonObjectGrupos = null;

        if(jsonGrupos != null){
            try {
                jsonObjectGrupos = jsonGrupos.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String resultado = (jsonObjectGrupos.optString("resultado"));


            if (!resultado.equals("Null")) {
                al_grupos.removeAll(al_grupos);

                try {

                    for (int i = 0; i < jsonGrupos.length(); i++) {

                        Grupo g = new Grupo();
                        JSONObject jsonObject = null;
                        jsonObject = jsonGrupos.getJSONObject(i);

                        g.setId_grupo(jsonObject.optInt("id_grupo"));
                        g.setNombre(jsonObject.optString("nombre"));
                        g.setCategoria(jsonObject.optString("categoria"));


                        al_grupos.add(g);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

        if(jsonSesiones != null){
            try {
                jsonObjectSesiones = jsonSesiones.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String resultado = (jsonObjectSesiones.optString("resultado"));

            if(resultado.equals("Eliminado")){
                Toast.makeText(getApplicationContext(), getString(R.string.GrupoEliminado), Toast.LENGTH_SHORT).show();
                adapter = new AdapterSesion(this, al_sesiones);
                lista_sesiones.setAdapter(adapter);
            }

            else if (!resultado.equals("Null")) {
                al_sesiones.removeAll(al_sesiones);

                try {

                    for (int i = 0; i < jsonSesiones.length(); i++) {

                        Sesiones s = new Sesiones();
                        JSONObject jsonObject = null;
                        jsonObject = jsonSesiones.getJSONObject(i);

                        s.setId_sesion(jsonObject.optInt("id_sesion"));
                        s.setId_grupo(jsonObject.optInt("id_grupo"));
                        s.setId_entrenamiento(jsonObject.optInt("id_grupo"));
                        s.setFecha_hora_inicio(jsonObject.optString("fecha_hora_inicio"));
                        s.setFecha_hora_fin(jsonObject.optString("fecha_hora_fin"));
                        s.setRealizada(jsonObject.optBoolean("realizada"));
                        s.setMotivo_cancelacion(jsonObject.optString("motivo_cancelacion"));
                        s.setValoracion(jsonObject.optString("valoracion"));
                        s.setId_entrenador(jsonObject.optInt("id_entrenador"));

                        al_sesiones.add(s);

                    }



                    adapter = new AdapterSesion(this, al_sesiones);
                    lista_sesiones.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }


    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }


}

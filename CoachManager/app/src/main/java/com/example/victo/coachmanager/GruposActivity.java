package com.example.victo.coachmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    private ArrayAdapter<Grupo> adapterGrupos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupos);

        Bundle objecteEnviat = getIntent().getExtras();
        lista_grupos = (ListView) findViewById(R.id.lv_lista_grupos);
        al_grupos = new ArrayList<Grupo>();




        if(objecteEnviat != null){
            Integer id_persona = (Integer) objecteEnviat.getSerializable("i");
        }




        cargarWebService();



        lista_grupos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(GruposActivity.this,VerGrupoActivity.class);
                Grupo g = new Grupo();
                g = al_grupos.get(position);
                intent.putExtra("grupo", g);
                startActivityForResult(intent, 1);

            }
        });

        FloatingActionButton flbtnAñadirGrupo = (FloatingActionButton) findViewById(R.id.flbtnAñadirGrupo);

        flbtnAñadirGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GruposActivity.this, AnyadirGrupoActivity.class);
                startActivityForResult(intent,1);
            }
        });

        lista_grupos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int pos, long l) {

                AlertDialog.Builder confirmacio = new AlertDialog.Builder(GruposActivity.this);
                confirmacio.setTitle("Eliminar Grupo"); //Óscar modifica esto

                String nombre = al_grupos.get(pos).getNombre();
                confirmacio.setMessage("Quieres eliminar el grupo " + nombre + "?"); //Óscar modifica esto
                confirmacio.setCancelable(false);

                confirmacio.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.out.println(lista_grupos.getItemAtPosition(pos));
                        aceptar(al_grupos.get(pos));
                    }
                });

                confirmacio.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                confirmacio.show();

                return true;
            }

            private void aceptar(Grupo g) {

                eliminarGrupo(g);
                al_grupos.remove(g);

            }
        });

    }



    public void eliminarGrupo(Grupo g){

        g.getId_grupo();


        String url = "http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_DeleteGrupo.php?id_grupo="+String.valueOf(g.getId_grupo());


        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        cargarWebService();
    }

    private void cargarWebService() {

        String id_entrenador = ((ObtenerIDs) this.getApplication()).getId_entrenador();

        String url="http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_Grupos.php?id_entrenador="+id_entrenador;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);


    }

    @Override
    public void onResponse(JSONObject response) {

        JSONArray json = response.optJSONArray("grupos");

        JSONObject jsonObject2 = null;

        try {
            jsonObject2 = json.getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String resultado = (jsonObject2.optString("resultado"));

        if(resultado.equals("Eliminado")){
            Toast.makeText(getApplicationContext(), "Grupo eliminado", Toast.LENGTH_SHORT).show();
            adapter = new AdapterGrupo(this, al_grupos);
            lista_grupos.setAdapter(adapter);
        }

        else if (!resultado.equals("Null")) {
            al_grupos.removeAll(al_grupos);

            try {

                for (int i = 0; i < json.length(); i++) {

                    Grupo g = new Grupo();
                    JSONObject jsonObject = null;
                    jsonObject = json.getJSONObject(i);

                    g.setId_grupo(jsonObject.optInt("id_grupo"));
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
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), getString(R.string.errorConexionBD), Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());
    }
}
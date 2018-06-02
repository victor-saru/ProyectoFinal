package com.example.victo.coachmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.victo.coachmanager.Adapters.AdapterAlumno;
import com.example.victo.coachmanager.Adapters.AdapterEjercicio;
import com.example.victo.coachmanager.Adapters.AdapterGrupo;
import com.example.victo.coachmanager.Entidades.Alumno;
import com.example.victo.coachmanager.Entidades.Deporte;
import com.example.victo.coachmanager.Entidades.Ejercicio;
import com.example.victo.coachmanager.Entidades.Grupo;
import com.example.victo.coachmanager.Entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VerEjerciciosDeporteActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    ArrayList<Ejercicio> al_ejercicios;
    ListView lista_ejercicios;
    Deporte deporte;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    AdapterEjercicio adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_ejercicios_deporte);

        al_ejercicios = new ArrayList<Ejercicio>();
        lista_ejercicios = (ListView) findViewById(R.id.lv_lista_ejercicios);
        FloatingActionButton btnAñadirEjercicio = (FloatingActionButton) findViewById(R.id.flbtnAñadirEjercicio);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarEjercicios);
        toolbar.setTitle(getString(R.string.Ejercicios));
        setSupportActionBar(toolbar);

        deporte = (Deporte) getIntent().getParcelableExtra("deporte");


        cargarWebService();

        btnAñadirEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerEjerciciosDeporteActivity.this,AnyadirEjercicioActivity.class);
                intent.putExtra("deporte", deporte);
                startActivityForResult(intent,1);
            }
        });


        lista_ejercicios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(VerEjerciciosDeporteActivity.this,VerEjercicioActivity.class);
                Ejercicio e = new Ejercicio();
                e = al_ejercicios.get(position);

                System.out.println(e.getDescripcion());
                intent.putExtra("ejercicio", e);
                startActivityForResult(intent,1);

            }
        });

        lista_ejercicios.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int pos, long l) {

                AlertDialog.Builder confirmacio = new AlertDialog.Builder(VerEjerciciosDeporteActivity.this);
                confirmacio.setTitle(getString(R.string.EliminarEjercicio));

                String nombre = al_ejercicios.get(pos).getNombre();
                confirmacio.setMessage(getString(R.string.EliminarEjercicioPregunta) + nombre + "?");
                confirmacio.setCancelable(false);

                confirmacio.setPositiveButton(getString(R.string.Si), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        aceptar(al_ejercicios.get(pos));
                    }
                });

                confirmacio.setNegativeButton(getString(R.string.Cancelar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                confirmacio.show();

                return true;
            }

            private void aceptar(Ejercicio e) {

                eliminarAlumno(e);
                al_ejercicios.remove(e);


            }

        });

    }

    private void eliminarAlumno(Ejercicio e) {

        System.out.println(e.getNombre());

        String url = "http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_DeleteEjercicio.php?id_ejercicio="+String.valueOf(e.getId_ejercicio());
        System.out.println(url);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        cargarWebService();
    }

    private void cargarWebService() {

        String id_entrenador = ((ObtenerIDs) this.getApplication()).getId_entrenador();

        String url="http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_Ejercicios.php?id_entrenador="+id_entrenador
                +"&id_deporte="+deporte.getId_deporte();

        System.out.println(url);


        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {

        JSONArray json = response.optJSONArray("ejercicios");

        JSONObject jsonObjectEjercicios=null;

        try {
            jsonObjectEjercicios = json.getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String resultado = (jsonObjectEjercicios.optString("resultado"));

        if(resultado.equals("Eliminado")){
            Toast.makeText(getApplicationContext(), getString(R.string.EjercicioEliminado), Toast.LENGTH_SHORT).show();
            adapter = new AdapterEjercicio(this, al_ejercicios);
            lista_ejercicios.setAdapter(adapter);
        }

        else if(!resultado.equals("Null")) {
            al_ejercicios.removeAll(al_ejercicios);

            try {

                for (int i = 0; i < json.length(); i++) {
                    Ejercicio e = new Ejercicio();
                    JSONObject jsonObject = null;
                    jsonObject = json.getJSONObject(i);
                    e.setId_ejercicio(jsonObject.optInt("id_ejercicio"));
                    e.setNombre(jsonObject.optString("nombre"));
                    e.setDescripcion(jsonObject.optString("descripcion"));
                    e.setId_deporte(jsonObject.optInt("id_deporte"));
                    e.setId_entrenador(jsonObject.optInt("id_entrenador"));


                    al_ejercicios.add(e);


                }

                adapter = new AdapterEjercicio(this, al_ejercicios);
                lista_ejercicios.setAdapter(adapter);

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

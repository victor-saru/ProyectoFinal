package com.example.victo.coachmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAlumnos);
        toolbar.setTitle(getString(R.string.Alumnos));
        setSupportActionBar(toolbar);

        int id;

        al_alumnos = new ArrayList<Alumno>();
        lista_alumnos = (ListView) findViewById(R.id.lv_lista_alumnos);


        cargarWebService();

        Collections.sort(al_alumnos, new Comparator<Alumno>(){
            @Override
            public int compare(Alumno alumno, Alumno a) {
                return alumno.getNombre().compareTo(a.getNombre());
            }
        });



        lista_alumnos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(AlumnosActivity.this,VerAlumnoActivity.class);
                Alumno a = new Alumno();
                a = al_alumnos.get(position);

                intent.putExtra("alumno", a);
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

        lista_alumnos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int pos, long l) {

                AlertDialog.Builder confirmacio = new AlertDialog.Builder(AlumnosActivity.this);
                confirmacio.setTitle(getString(R.string.EliminarAlumno));

                String nombre = al_alumnos.get(pos).getNombre()+ " " + al_alumnos.get(pos).getPrimer_apellido() + " " + al_alumnos.get(pos).getSegundo_apellido();
                confirmacio.setMessage(getString(R.string.EliminarAlumnoPregunta) + nombre + "?");
                confirmacio.setCancelable(false);

                confirmacio.setPositiveButton(getString(R.string.Si), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        aceptar(al_alumnos.get(pos));
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

            private void aceptar(Alumno a) {

                eliminarAlumno(a);
                al_alumnos.remove(a);


            }

        });
    }

    public void eliminarAlumno(Alumno a){

        a.getId_alumno();
        a.getId_persona();

        String url = "http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_DeleteAlumno.php?id_alumno="+String.valueOf(a.getId_alumno())
                +"&id_persona="+String.valueOf(a.getId_persona());


        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        cargarWebService();
    }

    private void cargarWebService(){
        String id_entrenador = ((ObtenerIDs) this.getApplication()).getId_entrenador();

        String url="http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_Alumnos.php?id_entrenador="+id_entrenador;


        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {

        JSONArray json = response.optJSONArray("alumnos");

        JSONObject jsonObject2=null;

        try {
            jsonObject2 = json.getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String resultado = (jsonObject2.optString("resultado"));

        if(resultado.equals("Eliminado")){
            Toast.makeText(getApplicationContext(), getString(R.string.AlumnoEliminado), Toast.LENGTH_SHORT).show();
            adapter = new AdapterAlumno(this, al_alumnos);
            lista_alumnos.setAdapter(adapter);
        }

        else if(!resultado.equals("Null")){
            al_alumnos.removeAll(al_alumnos);

            try {

                for(int i = 0; i < json.length(); i++){
                    Alumno a = new Alumno();
                    JSONObject jsonObject = null;
                    jsonObject=json.getJSONObject(i);
                    a.setId_alumno(jsonObject.optInt("id_alumno"));
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
                    a.setId_persona(jsonObject.optInt("id_persona"));

                    al_alumnos.add(a);


                }

                adapter = new AdapterAlumno(this, al_alumnos);
                lista_alumnos.setAdapter(adapter);

            }catch(JSONException e){
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
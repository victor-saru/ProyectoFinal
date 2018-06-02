package com.example.victo.coachmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.victo.coachmanager.Adapters.AdapterEjercicio;
import com.example.victo.coachmanager.Entidades.Alumno;
import com.example.victo.coachmanager.Entidades.Deporte;
import com.example.victo.coachmanager.Entidades.Ejercicio;
import com.example.victo.coachmanager.Entidades.Entrenamiento;
import com.example.victo.coachmanager.Entidades.Grupo;
import com.example.victo.coachmanager.Entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EditarEntrenamientoActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    EditText edNombreEntre;
    Spinner SpDeporteEntre;
    Button btnGuardarEditEntre;
    ListView lv_ejercicios_deporte;
    ImageView btnVolver;
    ArrayList<Deporte> deportes;

    String resultado;
    String resultado2;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    Entrenamiento entrenamiento;
    ArrayList<Ejercicio> al_ejercicios;
    ArrayList<String> nombreEjercicios;
    ArrayAdapter<String> adapterEjercicios;
    ArrayList<Ejercicio> ejerciciosEntrenamiento;
    private ArrayList<Ejercicio> ejerciciosSelecionados;
    Deporte deporte;
    AdapterEjercicio adapterEjercicio;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_entrenamiento);

        edNombreEntre = (EditText) findViewById(R.id.edNombreEntreEditar);
        SpDeporteEntre = (Spinner) findViewById(R.id.SpDeporteEntreEditar);
        btnGuardarEditEntre = (Button) findViewById(R.id.btnGuardarEditEntre);
        lv_ejercicios_deporte = (ListView) findViewById(R.id.lv_ejercicios_deporteEditar);
        btnVolver = (ImageView) findViewById(R.id.btnVolverEditarEntre);

        al_ejercicios = new ArrayList<>();
        nombreEjercicios = new ArrayList<>();
        ejerciciosEntrenamiento = new ArrayList<>();
        ejerciciosSelecionados = new ArrayList<>();
        deportes = new ArrayList<>();

        entrenamiento = (Entrenamiento) getIntent().getParcelableExtra("entrenamiento");

        edNombreEntre.setText(entrenamiento.getNombre());

        cargarWebServiceDeportes();
        cargarWebServiceEjerEntre();


        btnGuardarEditEntre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comprobarValores();

            }
        });



        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void comprobarValores() {

        if(edNombreEntre.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), getString(R.string.errorRellCampsObl), Toast.LENGTH_SHORT).show();
        }

        else{
            SparseBooleanArray checked = lv_ejercicios_deporte.getCheckedItemPositions();

            for(int i = 0; i < checked.size(); i++){
                int position = checked.keyAt(i);

                if(checked.valueAt(i)){
                    ejerciciosSelecionados.add(al_ejercicios.get(position));
                }
            }
            updateGrupoWebService();
        }

    }

    private void cargarWebServiceEjercicios() {

        String id_entrenador = ((ObtenerIDs) this.getApplication()).getId_entrenador();


        String url = "http://" + ((ObtenerIDs) this.getApplication()).getIp() + "/CoachManagerPHP/CoachManager_Ejercicios.php?id_entrenador=" + id_entrenador
                + "&id_deporte=" + String.valueOf(deporte.getId_deporte());


        System.out.println(url);

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);


    }

    private void cargarWebServiceEjerEntre() {
        String url="http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_EjerEntre.php?id_entrenamiento="+entrenamiento.getId_entrenamiento();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void cargarWebServiceDeportes() {
        String url = "http://" + ((ObtenerIDs) this.getApplication()).getIp() + "/CoachManagerPHP/CoachManager_Deportes.php";

        System.out.println(url);

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void updateGrupoWebService() {

        String url="http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_UpdateEntrenamiento.php?nombre="+edNombreEntre.getText().toString()
                +"&id_entrenamiento="+String.valueOf(entrenamiento.getId_entrenamiento())
                +"&id_entrenador="+String.valueOf(((ObtenerIDs) this.getApplication()).getId_entrenador())
                +"&id_deporte="+String.valueOf(deportes.get(SpDeporteEntre.getSelectedItemPosition()).getId_deporte());

        System.out.println(url);

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }


    @Override
    public void onResponse(JSONObject response) {

        JSONArray jsonDeportes = response.optJSONArray("deportes");
        JSONObject jsonObjectDeportes = null;

        JSONArray jsonEjercicios = response.optJSONArray("ejerentre");
        JSONObject jsonObjectEjercicios = null;

        JSONArray jsonEjerciciosDeporte = response.optJSONArray("ejercicios");
        JSONObject jsonObjectEjerciciosDeporte = null;

        JSONArray jsonUpdateEntre = response.optJSONArray("entrenamiento");
        JSONObject jsonObjectUpdateEntre = null;

        JSONArray jsonEliminar = response.optJSONArray("eliminar");
        JSONObject jsonObjectEliminar= null;


        if(jsonEliminar != null){
            try {
                jsonObjectEliminar = jsonEliminar.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            resultado = (jsonObjectEliminar.optString("resultado"));

            insertEjerciciosEntrenamientosWebService();
            finish();

        }


        if(jsonUpdateEntre != null){
            try {
                jsonObjectUpdateEntre = jsonUpdateEntre.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            resultado = (jsonObjectUpdateEntre.optString("resultado"));

            if(resultado.equals("NombreRepetido")){
                Toast.makeText(getApplicationContext(), getString(R.string.GrupoExistente), Toast.LENGTH_SHORT).show();
            }

            else if(resultado.equals("Null")){
                Toast.makeText(getApplicationContext(), getString(R.string.errorRellCampsObl), Toast.LENGTH_SHORT).show();
            }


            else{
                Toast.makeText(getApplicationContext(), getString(R.string.GuardarCambios), Toast.LENGTH_SHORT).show();
                deleteGrupoAlumnoWebService();
            }
        }

        if(jsonEjerciciosDeporte != null){

            adapterEjercicios = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, nombreEjercicios);
            lv_ejercicios_deporte.setAdapter(adapterEjercicios);

            try {
                jsonObjectEjerciciosDeporte = jsonEjerciciosDeporte.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            resultado = (jsonObjectEjerciciosDeporte.optString("resultado"));

            if (!resultado.equals("Null")) {
                al_ejercicios.removeAll(al_ejercicios);


                try {
                    for(int i = 0; i < jsonEjerciciosDeporte.length(); i++){
                        Ejercicio e = new Ejercicio();
                        JSONObject jsonObject3 = null;

                        jsonObject3=jsonEjerciciosDeporte.getJSONObject(i);

                        e.setId_ejercicio(jsonObject3.optInt("id_ejercicio"));
                        e.setNombre(jsonObject3.optString("nombre"));
                        e.setDescripcion(jsonObject3.optString("descripcion"));
                        e.setId_deporte(jsonObject3.optInt("id_deporte"));
                        e.setId_entrenador(jsonObject3.optInt("id_entrenador"));


                        al_ejercicios.add(e);
                    }


                    for(int i = 0; i < al_ejercicios.size(); i++){
                        nombreEjercicios.add(al_ejercicios.get(i).getNombre());
                    }
                    adapterEjercicios = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, nombreEjercicios);
                    lv_ejercicios_deporte.setAdapter(adapterEjercicios);

                } catch (JSONException e1) {
                    e1.printStackTrace();
                }


            }


        }


        if(jsonEjercicios != null){
            try {
                jsonObjectEjercicios = jsonEjercicios.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            resultado = (jsonObjectEjercicios.optString("resultado"));

            if (!resultado.equals("Null")) {

                for(int i = 0; i < jsonEjercicios.length(); i++){
                    Ejercicio e = new Ejercicio();
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = jsonEjercicios.getJSONObject(i);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }

                    e.setId_ejercicio(jsonObject.optInt("id_ejercicio"));
                    e.setNombre(jsonObject.optString("nombre"));
                    e.setDescripcion(jsonObject.optString("descripcion"));
                    e.setId_deporte(jsonObject.optInt("id_deporte"));
                    e.setId_entrenador(jsonObject.optInt("id_entrenador"));

                    ejerciciosEntrenamiento.add(e);

                }

            }

        }

        if(jsonDeportes != null){
            try {
                jsonObjectDeportes = jsonDeportes.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            resultado2 = (jsonObjectDeportes.optString("resultado"));



            if (!resultado2.equals("Null")) {
                deportes.removeAll(deportes);

                try {
                    for(int i = 0; i < jsonDeportes.length(); i++){
                        Deporte d = new Deporte();
                        JSONObject jsonObject = null;
                        jsonObject=jsonDeportes.getJSONObject(i);
                        d.setId_deporte(jsonObject.optInt("id_deporte"));
                        d.setNombre(jsonObject.optString("nombre"));


                        deportes.add(d);
                    }



                    String[] DeporteEntrenamiento = new String[deportes.size()];

                    for(int i = 0; i < deportes.size(); i++){
                        DeporteEntrenamiento[i] = deportes.get(i).getNombre();
                    }
                    SpDeporteEntre.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, DeporteEntrenamiento));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for(int i = 0; i < deportes.size(); i++){
                    if(deportes.get(i).getId_deporte() == entrenamiento.getId_deporte())
                        deporte = deportes.get(i);
                }
                System.out.println(deporte.getNombre().toString());
                System.out.println(deportes.indexOf(deporte));

                SpDeporteEntre.post(new Runnable() {
                    @Override
                    public void run() {
                        SpDeporteEntre.setSelection(deportes.indexOf(deporte));
                    }
                });

                cargarWebServiceEjercicios();
            }
        }

        comprobarSeleccionador();

    }

    private void insertEjerciciosEntrenamientosWebService() {

        ArrayList urls = new ArrayList();

        for(int i = 0; i < ejerciciosSelecionados.size(); i++){
            String url="http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_InsertEjerciciosEntrenamientos.php?nombre="+edNombreEntre.getText().toString()
                    +"&id_ejercicio="+String.valueOf(ejerciciosSelecionados.get(i).getId_ejercicio())
                    +"&orden="+String.valueOf(i);
            urls.add(url);
            System.out.println(url);

        }

        for(int i = 0; i < urls.size(); i++){
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, (String) urls.get(i), null, this, this);
            VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
        }
    }

    private void deleteGrupoAlumnoWebService() {

        String url="http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_DeleteEjerEntre.php?id_entrenamiento="+entrenamiento.getId_entrenamiento();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void comprobarSeleccionador() {

        for (int i = 0; i < al_ejercicios.size(); i++) {

            for(int j = 0; j < ejerciciosEntrenamiento.size(); j++){

                if(al_ejercicios.get(i).getId_ejercicio() == ejerciciosEntrenamiento.get(j).getId_ejercicio())
                    lv_ejercicios_deporte.setItemChecked(i, true);

            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), getString(R.string.errorConexionBD), Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());
    }
}

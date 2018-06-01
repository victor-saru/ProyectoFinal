package com.example.victo.coachmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
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
import com.android.volley.toolbox.Volley;
import com.example.victo.coachmanager.Adapters.AdapterDeporte;
import com.example.victo.coachmanager.Entidades.Alumno;
import com.example.victo.coachmanager.Entidades.Deporte;
import com.example.victo.coachmanager.Entidades.Ejercicio;
import com.example.victo.coachmanager.Entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AnyadirEntrenamientoActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    EditText edNombreEntre;
    Spinner SpDeporteEntre;
    Button btnAñadirEntre;
    ListView lv_ejercicios_deporte;
    ImageView btnVolver;
    ArrayList<Ejercicio> al_ejercicios;
    private ArrayList<Ejercicio> ejerciciosSeleccionados;
    private ArrayAdapter<String> adapterEjercicios;
    private ArrayList<String> nombreEjercicios;
    private ArrayList<Deporte> deportes;


    String resultado;
    String resultado2;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anyadir_entrenamiento);

        edNombreEntre = (EditText) findViewById(R.id.edNombreEntreAñadir);
        SpDeporteEntre = (Spinner) findViewById(R.id.SpDeporteEntreAñadir);
        btnAñadirEntre = (Button) findViewById(R.id.btnAñadirEntre);
        lv_ejercicios_deporte = (ListView) findViewById(R.id.lv_ejercicios_deporteAñadir);
        btnVolver = (ImageView) findViewById(R.id.btnVolverAñadirEntre);

        request = Volley.newRequestQueue(getApplicationContext());
        nombreEjercicios = new ArrayList<String>();
        ejerciciosSeleccionados = new ArrayList<Ejercicio>();
        deportes = new ArrayList<>();
        al_ejercicios = new ArrayList<>();
        resultado = null;
        resultado2 = null;

        request = Volley.newRequestQueue(getApplicationContext());

        cargarWebServiceDeportes();


        SpDeporteEntre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                nombreEjercicios.removeAll(nombreEjercicios);
                al_ejercicios.removeAll(al_ejercicios);

                cargarWebServiceEjercicios(deportes.get(i));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnAñadirEntre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comprobarValores();
            }
        });


        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnyadirEntrenamientoActivity.this, EntrenamientosActivity.class);
                startActivityForResult(intent, 1);
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
                    ejerciciosSeleccionados.add(al_ejercicios.get(position));
                }
            }

            cargarWebService();
        }
    }

    private void cargarWebService() {

        String url="http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_InsertGrupo.php?nombre="+edNombreEntre.getText().toString()
                +"&id_deporte="+deportes.get(SpDeporteEntre.getSelectedItemPosition())
                +"&id_entrenador="+String.valueOf(((ObtenerIDs) this.getApplication()).getId_entrenador());

        System.out.println(url);

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void cargarWebServiceDeportes() {
        String url = "http://" + ((ObtenerIDs) this.getApplication()).getIp() + "/CoachManagerPHP/CoachManager_Deportes.php";

        System.out.println(url);

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);

    }

    private void cargarWebServiceEjercicios(Deporte deporte) {

        String id_entrenador = ((ObtenerIDs) this.getApplication()).getId_entrenador();

        String url = "http://" + ((ObtenerIDs) this.getApplication()).getIp() + "/CoachManagerPHP/CoachManager_Ejercicios.php?id_entrenador=" + id_entrenador
                + "&id_deporte=" + String.valueOf(deporte.getId_deporte());


        System.out.println(url);

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
    }

    @Override
    public void onResponse(JSONObject response) {

       JSONArray jsonDeportes = response.optJSONArray("deportes");
       JSONObject jsonObjectDeportes = null;
       JSONArray jsonEjercicios = response.optJSONArray("ejercicios");
       JSONObject jsonObjectEjercicios = null;


        if(jsonEjercicios != null){

            adapterEjercicios = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, nombreEjercicios);
            lv_ejercicios_deporte.setAdapter(adapterEjercicios);

            try {
                jsonObjectEjercicios = jsonEjercicios.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            resultado = (jsonObjectEjercicios.optString("resultado"));



            if (!resultado.equals("Null")) {
                al_ejercicios.removeAll(al_ejercicios);


                try {
                    for(int i = 0; i < jsonEjercicios.length(); i++){
                        Ejercicio e = new Ejercicio();
                        JSONObject jsonObject3 = null;

                        jsonObject3=jsonEjercicios.getJSONObject(i);

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


                    SpDeporteEntre = (Spinner) findViewById(R.id.SpDeporteEntreAñadir);
                    String[] DeporteEntrenamiento = new String[deportes.size()];

                    for(int i = 0; i < deportes.size(); i++){
                        DeporteEntrenamiento[i] = deportes.get(i).getNombre();
                    }
                    SpDeporteEntre.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, DeporteEntrenamiento));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
       }


    }

    @Override
    public void onErrorResponse (VolleyError error){

    }
}
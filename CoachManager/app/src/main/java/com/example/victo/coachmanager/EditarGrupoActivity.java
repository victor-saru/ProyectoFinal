package com.example.victo.coachmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.victo.coachmanager.Adapters.AdapterAlumno;
import com.example.victo.coachmanager.Entidades.Alumno;
import com.example.victo.coachmanager.Entidades.Grupo;
import com.example.victo.coachmanager.Entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class EditarGrupoActivity extends AppCompatActivity implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener {

    EditText edNombreGrupo;
    EditText edCategoriaGrupo;
    Button btnGuardarCambios;
    ListView lv_alumnos_grupo;
    ImageView btnVolver;
    String resultado;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    Grupo grupo;
    ArrayList<Alumno> al_alumnos;
    ArrayList<String> nombreAlumnos;
    ArrayAdapter<String> adapterAlumnos;
    ArrayList<Alumno> alumnosGrupo;
    private ArrayList<Alumno> alumnosSeleccionados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_grupo);

        edNombreGrupo = (EditText) findViewById(R.id.edNombreGrupoEditar);
        edCategoriaGrupo = (EditText) findViewById(R.id.edCategoriaGrupoEditar);
        btnGuardarCambios = (Button) findViewById(R.id.btnGuardarEditGrupo);
        lv_alumnos_grupo = (ListView) findViewById(R.id.lv_alumnos_grupoEditar);
        btnVolver = (ImageView) findViewById(R.id.btnVolverEditarGrupo);

        al_alumnos = new ArrayList<>();
        nombreAlumnos = new ArrayList<>();
        alumnosGrupo = new ArrayList<>();
        alumnosSeleccionados = new ArrayList<>();


        grupo = (Grupo) getIntent().getParcelableExtra("grupo");

        edNombreGrupo.setText(grupo.getNombre());
        edCategoriaGrupo.setText(grupo.getCategoria());
        cargarWebServiceAlumnosGrupo();
        cargarWebServiceAlumnos();




        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        if(edNombreGrupo.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), getString(R.string.errorRellCampsObl), Toast.LENGTH_SHORT).show();
        }

        else if(comprovarIntegerYString(edNombreGrupo.getText().toString())){
            Toast.makeText(getApplicationContext(), getString(R.string.errorNombre), Toast.LENGTH_SHORT).show();
        }

        else{

            SparseBooleanArray checked = lv_alumnos_grupo.getCheckedItemPositions();

            for(int i = 0; i < checked.size(); i++){
                int position = checked.keyAt(i);

                if(checked.valueAt(i)){
                    alumnosSeleccionados.add(al_alumnos.get(position));
                }
            }


            updateGrupoWebService();
        }
    }



    private boolean comprovarIntegerYString(String s) {

        boolean resultado;

        try {
            Integer.parseInt(s);
            Float.parseFloat(s);
            resultado = true;
        }catch(NumberFormatException e){
            resultado = false;
        }

        return resultado;
    }

    private void updateGrupoWebService() {

        String url="http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_UpdateGrupo.php?nombre="+edNombreGrupo.getText().toString()
                +"&categoria="+edCategoriaGrupo.getText().toString()
                +"&id_entrenador="+String.valueOf(((ObtenerIDs) this.getApplication()).getId_entrenador())
                +"&id_grupo="+String.valueOf(grupo.getId_grupo());

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void cargarWebServiceAlumnosGrupo() {
        String url="http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_AlumnosGrupos.php?id_grupo="+String.valueOf(grupo.getId_grupo());

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    public void cargarWebServiceAlumnos() {
        String id_entrenador = ((ObtenerIDs) this.getApplication()).getId_entrenador();

        String url = "http://" + ((ObtenerIDs) this.getApplication()).getIp() + "/CoachManagerPHP/CoachManager_Alumnos.php?id_entrenador=" + id_entrenador;


        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
    }

    @Override
    public void onClick(View view) {

    }

    public void comprobarSeleccionados(){
        for (int i = 0; i < al_alumnos.size(); i++) {

            for(int j = 0; j < alumnosGrupo.size(); j++){

                if(al_alumnos.get(i).getId_alumno() == alumnosGrupo.get(j).getId_alumno())

                lv_alumnos_grupo.setItemChecked(i, true);

            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), getString(R.string.errorConexionBD), Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());
    }





    @Override
    public void onResponse(JSONObject response) {
        JSONArray json = response.optJSONArray("alumnosgrupo");
        JSONArray json2 = response.optJSONArray("alumnos");
        JSONArray json4 = response.optJSONArray("grupo");
        JSONArray json5 = response.optJSONArray("eliminar");

        JSONObject jsonObject1 = null;
        JSONObject jsonObject2 = null;
        JSONObject jsonObject4 = null;
        JSONObject jsonObject5 = null;

        if(json5 != null){
            try {
                jsonObject5 = json5.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            resultado = (jsonObject5.optString("resultado"));

            insertGrupoAlumnoWebService();
            finish();

        }

        if(json4 != null){
            try {
                jsonObject4 = json4.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            resultado = (jsonObject4.optString("resultado"));

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

        if (json != null) {
            try {
                jsonObject1 = json.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            resultado = (jsonObject1.optString("resultado"));

            if (!resultado.equals("Null")) {

                try {

                    for (int i = 0; i < json.length(); i++) {
                        Alumno a = new Alumno();
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(i);
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

                        alumnosGrupo.add(a);

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }


        if (json2 != null) {
            try {
                jsonObject2 = json2.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            resultado = (jsonObject2.optString("resultado"));

            if (!resultado.equals("Null")) {
               al_alumnos.removeAll(al_alumnos);

                try {

                    for (int i = 0; i < json2.length(); i++) {
                        Alumno a = new Alumno();
                        JSONObject jsonObject3 = null;
                        jsonObject3 = json2.getJSONObject(i);
                        a.setId_alumno(jsonObject3.optInt("id_alumno"));
                        a.setNombre(jsonObject3.optString("nombre"));
                        a.setPrimer_apellido(jsonObject3.optString("primer_apellido"));
                        a.setSegundo_apellido(jsonObject3.optString("segundo_apellido"));
                        a.setDni(jsonObject3.optString("dni"));
                        a.setFecha_nacimiento(jsonObject3.optString("fecha_nacimiento"));
                        a.setGenero(jsonObject3.optString("genero"));
                        a.setMano_dom(jsonObject3.optString("mano_dom"));
                        a.setPie_dom(jsonObject3.optString("pie_dom"));
                        a.setObservaciones(jsonObject3.optString("observaciones"));
                        a.setMovil(jsonObject3.optInt("movil"));
                        a.setPeso(jsonObject3.optInt("peso"));
                        a.setAltura(jsonObject3.optInt("altura"));
                        a.setId_persona(jsonObject3.optInt("id_persona"));

                        al_alumnos.add(a);

                    }



                    for (int i = 0; i < al_alumnos.size(); i++) {
                        nombreAlumnos.add(al_alumnos.get(i).getNombre() + " " + al_alumnos.get(i).getPrimer_apellido() + " " + al_alumnos.get(i).getSegundo_apellido());

                    }
                    adapterAlumnos = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, nombreAlumnos);
                    lv_alumnos_grupo.setAdapter(adapterAlumnos);




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        comprobarSeleccionados();
    }

    private void deleteGrupoAlumnoWebService() {

        String url="http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_DeleteAlumnosGrupo.php?id_grupo="+grupo.getId_grupo();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void insertGrupoAlumnoWebService() {

        ArrayList urls = new ArrayList();

        for(int i = 0; i < alumnosSeleccionados.size(); i++){
            String url="http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_InsertGrupoAlumno.php?nombre="+edNombreGrupo.getText().toString()
                    +"&id_alumno="+String.valueOf(alumnosSeleccionados.get(i).getId_alumno());
            urls.add(url);
        }


        for(int i = 0; i < urls.size(); i++){
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, (String) urls.get(i), null, this, this);
            VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
        }


    }


}

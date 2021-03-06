package com.example.victo.coachmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.View;
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
import com.android.volley.toolbox.Volley;
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

public class AnyadirGrupoActivity extends AppCompatActivity implements View.OnClickListener , Response.Listener<JSONObject>, Response.ErrorListener{

    EditText edNombreGrupo;
    EditText edCategoriaGrupo;
    Button btnAñadirGrupo;
    ListView lv_alumnos_grupo;
    ImageView btnVolver;
    ArrayList<Alumno> al_alumnos;
    private ArrayList<Alumno> alumnosSeleccionados;
    private ArrayAdapter<String> adapterAlumnos;
    private ArrayList<String> nombreAlumnos;





    String resultado;
    String resultado2;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anyadir_grupo);

        edNombreGrupo = (EditText) findViewById(R.id.edNombreGrupoAñadir);
        edCategoriaGrupo = (EditText) findViewById(R.id.edCategoriaGrupoAñadir);
        btnAñadirGrupo = (Button) findViewById(R.id.btnAñadirGrupo);
        lv_alumnos_grupo = (ListView)findViewById(R.id.lv_alumnos_grupoAñadir);
        btnVolver = (ImageView) findViewById(R.id.btnVolverAñadirGrupo);
        al_alumnos = new ArrayList<Alumno>();

        btnAñadirGrupo.setOnClickListener(this);
        btnVolver.setOnClickListener(this);

        request = Volley.newRequestQueue(getApplicationContext());

        nombreAlumnos = new ArrayList<String>();
        alumnosSeleccionados = new ArrayList<Alumno>();
        
        cargarWebServiceAlumnos();



    }

    private void cargarWebServiceAlumnos() {

        String id_entrenador = ((ObtenerIDs) this.getApplication()).getId_entrenador();

        String url="http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_Alumnos.php?id_entrenador="+id_entrenador;


        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnAñadirGrupo:{
                comprobarValores();
                //Intent intent = new Intent(AnyadirGrupoActivity.this, GruposActivity.class);
                //startActivityForResult(intent,1);
                break;
            }

            case R.id.btnVolverAñadirGrupo:{
                finish();
                break;
            }
        }
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


            cargarWebService();

        }
        
    }

    private void insertGrupoAlumnoWebService(){

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





    private void cargarWebService() {
        String url="http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_InsertGrupo.php?nombre="+edNombreGrupo.getText().toString()
                +"&categoria="+edCategoriaGrupo.getText().toString()
                +"&id_entrenador="+String.valueOf(((ObtenerIDs) this.getApplication()).getId_entrenador());

        //http://192.168.1.45/CoachManagerPHP/CoachManager_InsertGrupo.php?nombre=Prueba&categoria=Programadores&id_entrenador=2

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
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




    @Override
    public void onResponse(JSONObject response) {



        JSONArray json = response.optJSONArray("grupo");
        JSONArray json2 = response.optJSONArray("alumnos");
        JSONArray json3 = response.optJSONArray("gruposalumnos");
        JSONObject jsonObject=null;
        JSONObject jsonObject2=null;
        JSONObject jsonObject4=null;

        try {

            if(json3 != null){
                jsonObject4 = json3.getJSONObject(0);
                resultado = (jsonObject4.optString("resultado"));

            }

            if(json != null){
                jsonObject = json.getJSONObject(0);
                resultado = (jsonObject.optString("resultado"));

                if(resultado.equals("NombreRepetido")){
                    Toast.makeText(getApplicationContext(), getString(R.string.GrupoExistente), Toast.LENGTH_SHORT).show();
                }

                else if(resultado.equals("Null")){
                    Toast.makeText(getApplicationContext(), getString(R.string.errorRellCampsObl), Toast.LENGTH_SHORT).show();
                }


                else{
                    Toast.makeText(getApplicationContext(), getString(R.string.RegistradoExito), Toast.LENGTH_SHORT).show();
                    insertGrupoAlumnoWebService();
                    finish();
                }
            }


            if(json2 != null){
                jsonObject2 = json2.getJSONObject(0);
                resultado2 = (jsonObject2.optString("resultado"));

                if(!resultado2.equals("Null")){
                    al_alumnos.removeAll(al_alumnos);

                    try {

                        for(int i = 0; i < json2.length(); i++){
                            Alumno a = new Alumno();
                            JSONObject jsonObject3 = null;
                            jsonObject3=json2.getJSONObject(i);
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

                        for(int i = 0; i < al_alumnos.size(); i++){
                            nombreAlumnos.add(al_alumnos.get(i).getNombre() + " " + al_alumnos.get(i).getPrimer_apellido() + " " + al_alumnos.get(i).getSegundo_apellido());
                        }
                        adapterAlumnos = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, nombreAlumnos);
                        lv_alumnos_grupo.setAdapter(adapterAlumnos);

                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                }
            }


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

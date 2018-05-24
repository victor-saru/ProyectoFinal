package com.example.victo.coachmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
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
import com.example.victo.coachmanager.Entidades.Alumno;
import com.example.victo.coachmanager.Entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AnyadirGrupoActivity extends AppCompatActivity implements View.OnClickListener , Response.Listener<JSONObject>, Response.ErrorListener{

    EditText edNombreGrupo;
    EditText edCategoriaGrupo;
    Button btnAñadirGrupo;
    ListView lv_alumnos_grupo;
    ImageView btnVolver;
    ArrayList<Alumno> al_alumnos;

    String resultado;
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

        btnAñadirGrupo.setOnClickListener(this);
        btnVolver.setOnClickListener(this);

        request = Volley.newRequestQueue(getApplicationContext());

    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnAñadirGrupo:{
                comprobarValores();
                Intent intent = new Intent(AnyadirGrupoActivity.this, GruposActivity.class);
                startActivityForResult(intent,1);
                break;
            }

            case R.id.btnVolverAñadirGrupo:{
                Intent intent = new Intent(AnyadirGrupoActivity.this, GruposActivity.class);
                startActivityForResult(intent,1);
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
            cargarWebService();
        }
        
    }

    private void cargarWebService() {
        String url="http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_Alumnos.php";
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

        JSONArray json = response.optJSONArray("alumnos");

        try {

            for(int i = 0; i < json.length(); i++){
                Alumno a = new Alumno();
                JSONObject jsonObject = null;
                jsonObject=json.getJSONObject(i);

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

                al_alumnos.add(a);


            }

    } catch (JSONException e) {
            e.printStackTrace();
        }
        }
    @Override
    public void onErrorResponse(VolleyError error) {

    }


}

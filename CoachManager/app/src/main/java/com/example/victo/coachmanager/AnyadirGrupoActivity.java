package com.example.victo.coachmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.example.victo.coachmanager.Entidades.Grupo;
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
                //Intent intent = new Intent(AnyadirGrupoActivity.this, GruposActivity.class);
                //startActivityForResult(intent,1);
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
        JSONObject jsonObject=null;

        try {
            jsonObject = json.getJSONObject(0);
            resultado = (jsonObject.optString("resultado"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(resultado.equals("NombreRepetido")){
            Toast.makeText(getApplicationContext(), "Has introducido un nombre de grupo ya existente", Toast.LENGTH_SHORT).show();
        }

        else if(resultado.equals("Null")){
            Toast.makeText(getApplicationContext(), getString(R.string.errorRellCampsObl), Toast.LENGTH_SHORT).show();
        }

        else{
            Toast.makeText(getApplicationContext(), getString(R.string.RegistradoExito), Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), getString(R.string.errorConexionBD), Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());
    }


}

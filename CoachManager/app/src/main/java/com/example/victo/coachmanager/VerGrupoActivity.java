package com.example.victo.coachmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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

public class VerGrupoActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    TextView edNombreGrupo;
    TextView edCategoriaGrupo;
    Button btnEditarGrupo;
    ListView lv_alumnos_grupo;
    ArrayList<Alumno> al_alumnos;
    ImageView btnVolver;
    String resultado;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    Grupo grupo;
    AdapterAlumno adapterAlumnos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_grupo);

        edNombreGrupo = (TextView) findViewById(R.id.lblNombreGrupoVerValor);
        edCategoriaGrupo = (TextView) findViewById(R.id.lblCategoriaGrupoVerValor);
        btnEditarGrupo = (Button) findViewById(R.id.btnEditarGrupo);
        lv_alumnos_grupo = (ListView)findViewById(R.id.lv_alumnos_grupoVer);
        btnVolver = (ImageView) findViewById(R.id.btnVolverVerGrupo);

        grupo = (Grupo) getIntent().getParcelableExtra("grupo");

        edNombreGrupo.setText(grupo.getNombre());
        edCategoriaGrupo.setText(grupo.getCategoria());


        edNombreGrupo.setText(grupo.getNombre());
        edCategoriaGrupo.setText(grupo.getCategoria());

        al_alumnos = new ArrayList<>();

        cargarWebServiceAlumnos();

        /*Collections.sort(al_alumnos, new Comparator<Alumno>(){
            @Override
            public int compare(Alumno alumno, Alumno a) {
                return alumno.getNombre().compareTo(a.getNombre());
            }
        });*/

        request = Volley.newRequestQueue(getApplicationContext());

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnEditarGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerGrupoActivity.this, EditarGrupoActivity.class);
                intent.putExtra("grupo", grupo);
                startActivityForResult(intent,1);
            }
        });
    }

    private void cargarWebServiceAlumnos() {
        String url="http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_AlumnosGrupos.php?id_grupo="+String.valueOf(grupo.getId_grupo());
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
    }

    @Override
    public void onResponse(JSONObject response) {


        JSONArray json = response.optJSONArray("alumnosgrupo");
        JSONObject jsonObject1 = null;


        if (json != null) {
            try {
                jsonObject1 = json.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            resultado = (jsonObject1.optString("resultado"));

            if (!resultado.equals("Null")) {
                //al_alumnos.removeAll(al_alumnos);

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

                        al_alumnos.add(a);


                        adapterAlumnos = new AdapterAlumno(this, al_alumnos);
                        lv_alumnos_grupo.setAdapter(adapterAlumnos);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), getString(R.string.errorConexionBD), Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());

    }



}

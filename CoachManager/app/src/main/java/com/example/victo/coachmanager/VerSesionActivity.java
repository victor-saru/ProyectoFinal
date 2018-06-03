package com.example.victo.coachmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.victo.coachmanager.Adapters.AdapterAlumno;
import com.example.victo.coachmanager.Adapters.AdapterEntrenamiento;
import com.example.victo.coachmanager.Entidades.Alumno;
import com.example.victo.coachmanager.Entidades.Entrenamiento;
import com.example.victo.coachmanager.Entidades.Sesiones;
import com.example.victo.coachmanager.Entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VerSesionActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    TextView edFechaSesion;
    TextView edHoraInicioSesion;
    TextView edHoraFinSesion;
    TextView edValoracionSesion;
    TextView edMotivoSesion;
    TextView spGruposSesion;
    CheckBox chbRealizadaSesion;
    ListView lv_entrenamientos_sesion;
    String resultado;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    ImageView btnVolver;
    Sesiones sesion;
    ArrayList<Entrenamiento> al_entrenamientos;
    AdapterEntrenamiento adapterEntrenamiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_sesion);

        edFechaSesion = (TextView) findViewById(R.id.edFechaInicioSesionVerSesion);
        edHoraInicioSesion = (TextView) findViewById(R.id.edInicioSesionVerSesion);
        edHoraFinSesion = (TextView) findViewById(R.id.edHoraFinVerSesion);
        edValoracionSesion = (TextView) findViewById(R.id.edValoracionSesionVerSesion);
        edMotivoSesion = (TextView) findViewById(R.id.edMotivoSesionVerSesion);
        spGruposSesion = (TextView) findViewById(R.id.SpGruposSesionVerSesion);
        chbRealizadaSesion  = (CheckBox) findViewById(R.id.chbRealizadaSesionVerSesion);
        lv_entrenamientos_sesion  = (ListView) findViewById(R.id.lv_entrenamientos_sesionVerSesion);
        btnVolver = (ImageView) findViewById(R.id.btnVolverAñadirSesionVerSesion);

        al_entrenamientos = new ArrayList<>();

        sesion = (Sesiones) getIntent().getParcelableExtra("sesion");

        edFechaSesion.setText(sesion.getFecha_hora_inicio());
        edHoraInicioSesion.setText(sesion.getFecha_hora_inicio());
        edHoraFinSesion.setText(sesion.getFecha_hora_fin());
        edValoracionSesion.setText(sesion.getValoracion());
        edMotivoSesion.setText(sesion.getMotivo_cancelacion());

        for(int i = 0; i < SesionesActivity.al_grupos.size(); i++){
            if(SesionesActivity.al_grupos.get(i).getId_grupo() == sesion.getId_grupo()){
                spGruposSesion.setText(SesionesActivity.al_grupos.get(i).getNombre());
                break;
            }
        }


        chbRealizadaSesion.setChecked(sesion.getRealizada());
        chbRealizadaSesion.setEnabled(false);

        cargarWebService();

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void cargarWebService() {
        String url="http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_EntrenoSesion.php?id_grupo="+String.valueOf(sesion.getId_grupo())
                +"&id_sesion="+ String.valueOf(sesion.getId_sesion());
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);

    }

    @Override
    public void onResponse(JSONObject response) {

        JSONArray jsonEntrenosSesion = response.optJSONArray("entrenosesion");
        JSONObject jsonObjectEntrenosSesion = null;

        if(jsonEntrenosSesion != null){

            try {
                jsonObjectEntrenosSesion = jsonEntrenosSesion.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            resultado = (jsonObjectEntrenosSesion.optString("resultado"));

            if (!resultado.equals("Null")) {
                //al_alumnos.removeAll(al_alumnos);

                try {

                    for (int i = 0; i < jsonEntrenosSesion.length(); i++) {
                        Entrenamiento e = new Entrenamiento();
                        JSONObject jsonObject = null;
                        jsonObject = jsonEntrenosSesion.getJSONObject(i);
                        e.setId_entrenamiento(jsonObject.optInt("id_entrenamiento"));
                        e.setId_deporte(jsonObject.optInt("id_deporte"));
                        e.setId_entrenador(jsonObject.optInt("id_deporte"));
                        e.setNombre(jsonObject.optString("nombre"));


                        al_entrenamientos.add(e);


                    }

                    adapterEntrenamiento = new AdapterEntrenamiento(this, al_entrenamientos);
                    lv_entrenamientos_sesion.setAdapter(adapterEntrenamiento);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }


    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

}

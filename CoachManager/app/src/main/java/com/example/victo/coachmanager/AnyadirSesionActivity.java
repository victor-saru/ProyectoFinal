package com.example.victo.coachmanager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.victo.coachmanager.Adapters.AdapterEntrenamiento;
import com.example.victo.coachmanager.Adapters.AdapterGrupo;
import com.example.victo.coachmanager.Entidades.Entrenamiento;
import com.example.victo.coachmanager.Entidades.Grupo;
import com.example.victo.coachmanager.Entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;

public class AnyadirSesionActivity extends AppCompatActivity implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener{

    TextView edFechaSesion;
    TextView edHoraInicioSesion;
    TextView edHoraFinSesion;
    EditText edValoracionSesion;
    EditText edMotivoSesion;
    Spinner spGruposSesion;
    CheckBox chbRealizadaSesion;
    ListView lv_entrenamientos_sesion;
    String resultado;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    AdapterEntrenamiento adapter;
    ArrayList<Grupo> al_grupos;
    ArrayList<Entrenamiento> al_entrenamientos;
    ArrayAdapter<String> adapterEntrenamientos;
    ArrayList<String> nombreEntrenamientos;
    Button btnAnyadirSesion;
    ArrayList<Entrenamiento> entrenamientosSelecionados;
    String sesionesPK;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anyadir_sesion);

        edFechaSesion = (TextView) findViewById(R.id.edFechaInicioSesion);

        edHoraInicioSesion = (TextView) findViewById(R.id.edHoraInicioSesion);
        edHoraFinSesion = (TextView) findViewById(R.id.edHoraFin);
        edValoracionSesion = (EditText) findViewById(R.id.edValoracionSesion);
        edMotivoSesion = (EditText) findViewById(R.id.edMotivoSesion);
        spGruposSesion = (Spinner) findViewById(R.id.SpGruposSesion);
        chbRealizadaSesion  = (CheckBox) findViewById(R.id.chbRealizadaSesion);
        lv_entrenamientos_sesion  = (ListView) findViewById(R.id.lv_entrenamientos_sesion);
        btnAnyadirSesion = (Button) findViewById(R.id.btnAñadirSesion);

        al_grupos = new ArrayList<>();
        al_entrenamientos = new ArrayList<>();
        nombreEntrenamientos = new ArrayList<>();
        entrenamientosSelecionados = new ArrayList<>();



       cargarWebServiceGrupos();
       cargarWebServiceEntrenamientos();

        edFechaSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        edHoraInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(edHoraInicioSesion);
            }
        });

        edHoraFinSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(edHoraFinSesion);
            }
        });

        btnAnyadirSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comprobarValores();
            }
        });

    }

    private void comprobarValores() {

        if(edFechaSesion.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), getString(R.string.errorRellCampsObl), Toast.LENGTH_SHORT).show();
        }

        else if(edHoraInicioSesion.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), getString(R.string.errorRellCampsObl), Toast.LENGTH_SHORT).show();
        }

        else{
            SparseBooleanArray checked = lv_entrenamientos_sesion.getCheckedItemPositions();

            if(checked != null){
                for(int i = 0; i < checked.size(); i++){
                    int position = checked.keyAt(i);

                    if(checked.valueAt(i)){
                        entrenamientosSelecionados.add(al_entrenamientos.get(position));
                    }
                }
            }


            cargarWebServiceObtenerPK();




        }
    }

    private void cargarWebServiceObtenerPK() {

        String url="http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_PrimaryKeySesiones.php";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);

    }

    private void cargarWebService() {

        ArrayList urls = new ArrayList();

        if(entrenamientosSelecionados != null){

            int checkRealizado;

            if(chbRealizadaSesion.isChecked())
                checkRealizado = 1;
            else
                checkRealizado = 0;

            System.out.println("Tamaño Selecionados: " + entrenamientosSelecionados.size());
            for(int i = 0; i < entrenamientosSelecionados.size(); i++){
                String url="http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_InsertSesion.php?id_sesion="+sesionesPK
                        +"&id_grupo="+String.valueOf(al_grupos.get(spGruposSesion.getSelectedItemPosition()).getId_grupo())
                        +"&id_entrenamiento="+String.valueOf(entrenamientosSelecionados.get(i).getId_entrenamiento())
                        +"&fecha_hora_inicio="+edFechaSesion.getText()+ " " + edHoraInicioSesion.getText() + ":00"
                        +"&fecha_hora_fin="+edFechaSesion.getText()+ " " + edHoraFinSesion.getText() + ":00"
                        +"&realizada="+String.valueOf(checkRealizado)
                        +"&motivo_cancelacion="+edMotivoSesion.getText()
                        +"&valoracion="+edValoracionSesion.getText()
                        +"&id_entrenador="+String.valueOf(((ObtenerIDs) this.getApplication()).getId_entrenador());

                System.out.println(url);
                urls.add(url);



            }

            System.out.println("Tamaño url: " + urls.size());


            for(int i = 0; i < urls.size(); i++){
                jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, (String) urls.get(i), null, this, this);
                VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
            }
        }

        else{
            Toast.makeText(getApplicationContext(), getString(R.string.ErrorEntreNoSelecionado), Toast.LENGTH_SHORT).show();
        }

    }

    private void cargarWebServiceEntrenamientos() {

        String id_entrenador = ((ObtenerIDs) this.getApplication()).getId_entrenador();

        String url="http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_Entrenamientos.php?id_entrenador="+id_entrenador;

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void cargarWebServiceGrupos() {

        String id_entrenador = ((ObtenerIDs) this.getApplication()).getId_entrenador();

        String url="http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_Grupos.php?id_entrenador="+id_entrenador;

        System.out.println(url);

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void showTimePickerDialog(final TextView ed) {

        Calendar c = Calendar.getInstance();
        int hora = c.get(Calendar.HOUR_OF_DAY);
        int minutos = c.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AnyadirSesionActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                ed.setText( selectedHour + ":" + selectedMinute);
            }
        }, hora, minutos, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = year + "-" + (month+1) + "-" + day;
                edFechaSesion.setText(selectedDate);
            }
        });
        newFragment.show(getFragmentManager(), "dataPicker");
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onResponse(JSONObject response) {

        JSONArray jsonEntrenamiento = response.optJSONArray("entrenamientos");

        JSONObject jsonObjectEntrenamiento = null;

        JSONArray jsonGrupos = response.optJSONArray("grupos");

        JSONObject jsonObjectGrupos = null;

        JSONArray jsonPK = response.optJSONArray("primarykey");

        JSONObject jsonObjectPK = null;

        JSONArray jsonSesion = response.optJSONArray("sesion");

        JSONObject jsonObjectSesion = null;

        if(jsonSesion != null){
            try {
                jsonObjectSesion = jsonSesion.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            resultado = (jsonObjectSesion.optString("resultado"));

            if (!resultado.equals("Null")) {
                Toast.makeText(getApplicationContext(), getString(R.string.RegistradoExito), Toast.LENGTH_SHORT).show();
                finish();
            }


        }

        if(jsonPK != null){
            try {
                jsonObjectPK = jsonPK.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            resultado = (jsonObjectPK.optString("resultado"));

            if (!resultado.equals("Null")) {
                sesionesPK = resultado.toString();
            }

            else{
                sesionesPK = "0";
            }

            cargarWebService();

            //System.out.println("PRIMARY KEY: " + sesionesPK);



        }

        if(jsonEntrenamiento != null) {
            try {
                jsonObjectEntrenamiento = jsonEntrenamiento.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            resultado = (jsonObjectEntrenamiento.optString("resultado"));

            if (!resultado.equals("Null")) {
                al_entrenamientos.removeAll(al_entrenamientos);

                try {

                    for (int i = 0; i < jsonEntrenamiento.length(); i++) {

                        Entrenamiento e = new Entrenamiento();
                        JSONObject jsonObject = null;
                        jsonObject = jsonEntrenamiento.getJSONObject(i);

                        e.setId_entrenamiento(jsonObject.optInt("id_entrenamiento"));
                        e.setNombre(jsonObject.optString("nombre"));
                        e.setId_deporte(jsonObject.optInt("id_deporte"));
                        e.setId_entrenador(jsonObject.optInt("id_entrenador"));


                        al_entrenamientos.add(e);

                    }


                    for (int i = 0; i < al_entrenamientos.size(); i++) {
                        String nombreDeporte = null;
                        for (int j = 0; j < MenuActivity.deportes.size(); j++) {
                            if (MenuActivity.deportes.get(j).getId_deporte() == al_entrenamientos.get(i).getId_deporte()) {
                                nombreDeporte = MenuActivity.deportes.get(j).getNombre();
                                break;
                            }
                        }
                        nombreEntrenamientos.add(al_entrenamientos.get(i).getNombre() + " - " + nombreDeporte);
                    }

                    adapterEntrenamientos = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, nombreEntrenamientos);
                    lv_entrenamientos_sesion.setAdapter(adapterEntrenamientos);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

        if(jsonGrupos != null){

            try {
                jsonObjectGrupos = jsonGrupos.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }

             resultado = (jsonObjectGrupos.optString("resultado"));


             if (!resultado.equals("Null")) {
                al_grupos.removeAll(al_grupos);

                try {

                    for (int i = 0; i < jsonGrupos.length(); i++) {

                        Grupo g = new Grupo();
                        JSONObject jsonObject = null;
                        jsonObject = jsonGrupos.getJSONObject(i);

                        g.setId_grupo(jsonObject.optInt("id_grupo"));
                        g.setNombre(jsonObject.optString("nombre"));
                        g.setCategoria(jsonObject.optString("categoria"));


                        al_grupos.add(g);

                    }

                    String[] GrupoSesion = new String[al_grupos.size()];

                    for(int i = 0; i < al_grupos.size(); i++){
                        GrupoSesion[i] = al_grupos.get(i).getNombre();
                        System.out.println("HOLA " + GrupoSesion[i]);
                    }

                    spGruposSesion.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, GrupoSesion));


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

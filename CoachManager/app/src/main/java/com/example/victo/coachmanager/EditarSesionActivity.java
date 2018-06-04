package com.example.victo.coachmanager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.victo.coachmanager.Entidades.Entrenamiento;
import com.example.victo.coachmanager.Entidades.Sesiones;
import com.example.victo.coachmanager.Entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class EditarSesionActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {



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
    ImageView btnVolver;
    Sesiones sesion;
    ArrayList<Entrenamiento> al_entrenamientos;
    ArrayList<Entrenamiento> al_entrenamientos_selecionados;
    ArrayAdapter<String> adapterEntrenamientos;
    Button btnEditarSesion;
    ArrayList<String> nombreEntrenamientos;
    ArrayList<Entrenamiento> entrenamientosSelecionados;
    String sesionesPK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_sesion);

        sesion = (Sesiones) getIntent().getParcelableExtra("sesion");



        edFechaSesion = (TextView) findViewById(R.id.edFechaInicioSesionEditarSesion);
        edHoraInicioSesion = (TextView) findViewById(R.id.edInicioSesionEditarSesion);
        edHoraFinSesion = (TextView) findViewById(R.id.edHoraFinEditarSesion);
        edValoracionSesion = (EditText) findViewById(R.id.edValoracionSesionEditarSesion);
        edMotivoSesion = (EditText) findViewById(R.id.edMotivoSesionEditarSesion);
        spGruposSesion = (Spinner) findViewById(R.id.SpGruposSesionEditarSesion);
        chbRealizadaSesion  = (CheckBox) findViewById(R.id.chbRealizadaSesionEditarSesion);
        lv_entrenamientos_sesion  = (ListView) findViewById(R.id.lv_entrenamientos_sesionEditarSesion);
        btnVolver = (ImageView) findViewById(R.id.btnVolverAñadirSesionEditarSesion);
        btnEditarSesion = (Button) findViewById(R.id.btnEditarSesionEditarSesion);


        edFechaSesion.setText(sesion.getFecha());
        edHoraInicioSesion.setText(sesion.getFecha_hora_inicio());
        edHoraFinSesion.setText(sesion.getFecha_hora_fin());

        edValoracionSesion.setText(sesion.getValoracion());
        edMotivoSesion.setText(sesion.getMotivo_cancelacion());
        chbRealizadaSesion.setChecked(sesion.getRealizada());
        edMotivoSesion.setEnabled(false);

        String[] GrupoSesion = new String[SesionesActivity.al_grupos.size()];

        for(int i = 0; i < SesionesActivity.al_grupos.size(); i++){
            GrupoSesion[i] = SesionesActivity.al_grupos.get(i).getNombre();

        }

        spGruposSesion.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, GrupoSesion));

        al_entrenamientos_selecionados = VerSesionActivity.al_entrenamientos;
        al_entrenamientos = new ArrayList<>();
        nombreEntrenamientos = new ArrayList<>();
        entrenamientosSelecionados = new ArrayList<>();

        cargarWebServiceEntrenamientos();

        btnEditarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comprobarValores();
            }
        });

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

        chbRealizadaSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chbRealizadaSesion.isChecked())
                    edMotivoSesion.setEnabled(true);
                else
                    edMotivoSesion.setEnabled(false);
            }
        });


    }

    private void showTimePickerDialog(final TextView ed) {

        Calendar c = Calendar.getInstance();
        int hora = c.get(Calendar.HOUR_OF_DAY);
        int minutos = c.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(EditarSesionActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

            if(entrenamientosSelecionados.size() >= 1)
                cargarWebServiceDeleteSesion();

            else
                Toast.makeText(getApplicationContext(), getString(R.string.ErrorEntreNoSelecionado), Toast.LENGTH_SHORT).show();


        }
    }

    private void cargarWebServiceDeleteSesion() {

        String url = "http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_DeleteSesion.php?id_sesion="+String.valueOf(sesion.getId_sesion());

        System.out.println(url);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }



    private void cargarWebServiceObtenerPK() {
        String url="http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_PrimaryKeySesiones.php";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);

    }

    private void comprobarSeleccionados() {

        for (int i = 0; i < al_entrenamientos.size(); i++) {

            for(int j = 0; j < al_entrenamientos_selecionados.size(); j++){

                if(al_entrenamientos.get(i).getId_entrenamiento() == al_entrenamientos_selecionados.get(j).getId_entrenamiento())
                    lv_entrenamientos_sesion.setItemChecked(i, true);

            }
        }
    }

    private void cargarWebServiceEntrenamientos() {
        String id_entrenador = ((ObtenerIDs) this.getApplication()).getId_entrenador();

        String url="http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_Entrenamientos.php?id_entrenador="+id_entrenador;
        System.out.println(url);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {

        JSONArray jsonDelete = response.optJSONArray("sesiones");

        JSONArray jsonPK = response.optJSONArray("primarykey");

        JSONObject jsonObjectPK = null;

        JSONArray jsonEntrenos = response.optJSONArray("entrenamientos");

        JSONObject jsonObjectEntreno = null;

        JSONArray jsonSesion = response.optJSONArray("sesion");

        JSONObject jsonObjectSesion = null;

        JSONArray jsonUpdateSesion = response.optJSONArray("updateSesion");

        JSONObject jsonObjectUpdateSesion = null;

        if(jsonUpdateSesion != null){
            try {
                jsonObjectUpdateSesion = jsonUpdateSesion.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            resultado = (jsonObjectUpdateSesion.optString("resultado"));

            if (!resultado.equals("Null")) {
                Toast.makeText(getApplicationContext(), getString(R.string.GuardarCambios), Toast.LENGTH_SHORT).show();
                finish();
            }


        }

        if(jsonSesion != null){
            try {
                jsonObjectSesion = jsonSesion.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            resultado = (jsonObjectSesion.optString("resultado"));

            if (!resultado.equals("Null")) {
                Toast.makeText(getApplicationContext(), getString(R.string.GuardarCambios), Toast.LENGTH_SHORT).show();
                finish();
            }


        }

        if(jsonDelete != null){
            cargarWebServiceObtenerPK();
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




        }

        if(jsonEntrenos !=null) {
            try {
                jsonObjectEntreno = jsonEntrenos.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String resultado = (jsonObjectEntreno.optString("resultado"));

            if (!resultado.equals("Null")) {
                al_entrenamientos.removeAll(al_entrenamientos);

                try {

                    for (int i = 0; i < jsonEntrenos.length(); i++) {

                        Entrenamiento e = new Entrenamiento();
                        JSONObject jsonObject = null;
                        jsonObject = jsonEntrenos.getJSONObject(i);

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


        comprobarSeleccionados();

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

                System.out.println(edFechaSesion.getText()+ " " + edHoraInicioSesion.getText() + ":00");
                System.out.println(edFechaSesion.getText()+ " " + edHoraFinSesion.getText() + ":00");

                String url="http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_InsertSesion.php?id_sesion="+sesionesPK
                        +"&id_grupo="+String.valueOf(SesionesActivity.al_grupos.get(spGruposSesion.getSelectedItemPosition()).getId_grupo())
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


            for(int i = 0; i < urls.size(); i++){
                jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, (String) urls.get(i), null, this, this);
                VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
            }
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }


}

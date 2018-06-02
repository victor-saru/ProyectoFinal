package com.example.victo.coachmanager;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.victo.coachmanager.Entidades.Entrenador;
import com.example.victo.coachmanager.Entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EditarPerfilActivity extends AppCompatActivity implements  Response.Listener<JSONObject>, Response.ErrorListener{

    EditText edCorreoRegistro;
    EditText edPasswordRegistro;
    EditText edNombreRegistro;
    EditText edPrimerApellidoRegistro;
    EditText edSegundoApellidoRegistro;
    EditText edDNIRegistro;
    EditText edMovilRegistro;
    EditText edFechaRegistro;
    Button btnRegistrarse;
    Spinner spinnerGeneroRegistro;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue ConsultaRequest;
    JsonObjectRequest jsonConsultaRequest;
    String resultado;
    int genero;
    String genero2;
    Entrenador entrenador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);


        edCorreoRegistro = (EditText) findViewById(R.id.edCorreoRegistroModificar);
        edPasswordRegistro = (EditText) findViewById(R.id.edPasswordRegistroModificar);
        edNombreRegistro = (EditText) findViewById(R.id.edNombreRegistroModificar);
        edPrimerApellidoRegistro = (EditText) findViewById(R.id.edPrimerApellidoRegistroModificar);
        edSegundoApellidoRegistro = (EditText) findViewById(R.id.edSegundoApellidoRegistroModificar);
        edDNIRegistro = (EditText) findViewById(R.id.edDNIRegistroModificar);
        edMovilRegistro = (EditText) findViewById(R.id.edMovilRegistroModificar);
        edFechaRegistro = (EditText) findViewById(R.id.edFechaRegistroModificar);
        edPasswordRegistro.setEnabled(false);

        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarModificar);
        spinnerGeneroRegistro = (Spinner) findViewById(R.id.spinnerGeneroRegistroModificar);
        String[] GeneroAlumno = {" ", getString(R.string.GeneroMasculino), getString(R.string.GeneroFemenino)};
        spinnerGeneroRegistro.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, GeneroAlumno));

        entrenador = (Entrenador) getIntent().getParcelableExtra("entrenador");

        edCorreoRegistro.setText(entrenador.getCorreo());
        edNombreRegistro.setText(entrenador.getNombre());
        edPrimerApellidoRegistro.setText(entrenador.getPrimer_apellido());
        edSegundoApellidoRegistro.setText(entrenador.getSegundo_apellido());
        edDNIRegistro.setText(entrenador.getDni());
        edMovilRegistro.setText(String.valueOf(entrenador.getMovil()));
        edFechaRegistro.setText(entrenador.getFecha_nacimiento());

        if(entrenador.getGenero().equals("Masculino") || entrenador.getGenero().equals("Masculí") || entrenador.getGenero().equals("Male"))
            genero = 1;
        else if (entrenador.getGenero().equals(" "))
            genero = 0;
        else
            genero = 2;

        spinnerGeneroRegistro.post(new Runnable() {
            @Override
            public void run() {
                spinnerGeneroRegistro.setSelection(genero);
            }
        });

        edFechaRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comprobarValores();
            }
        });

    }

    private void comprobarValores() {

        if(edMovilRegistro.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), getString(R.string.errorRellCampsObl), Toast.LENGTH_SHORT).show();
        }

        else if(!comprovarIntegerYString(edMovilRegistro.getText().toString())){
            Toast.makeText(getApplicationContext(), getString(R.string.errorMovil), Toast.LENGTH_SHORT).show();
        }

        else if(comprovarIntegerYString(edNombreRegistro.getText().toString())){
            Toast.makeText(getApplicationContext(), getString(R.string.errorNombre), Toast.LENGTH_SHORT).show();
        }

        else if(comprovarIntegerYString(edPrimerApellidoRegistro.getText().toString())){
            Toast.makeText(getApplicationContext(), getString(R.string.errorApellido), Toast.LENGTH_SHORT).show();
        }

        else if(comprovarIntegerYString(edSegundoApellidoRegistro.getText().toString())){
            Toast.makeText(getApplicationContext(), getString(R.string.errorApellido), Toast.LENGTH_SHORT).show();
        }

        else{

            if(spinnerGeneroRegistro.getSelectedItem().toString().equals("Masculino") || spinnerGeneroRegistro.getSelectedItem().toString().equals("Masculí") || spinnerGeneroRegistro.getSelectedItem().toString().equals("Male"))
                genero2 = "Masculino";
            else if (spinnerGeneroRegistro.getSelectedItem().toString().equals(" "))
                genero2 = " ";
            else
                genero2 = "Femenino";

            cargarWebService();
        }
    }

    private void cargarWebService() {

            String url="http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_UpdatePersona_Entrenador.php?nombre="+edNombreRegistro.getText().toString()
                    +"&primer_apellido="+edPrimerApellidoRegistro.getText().toString()
                    +"&segundo_apellido="+edSegundoApellidoRegistro.getText().toString()
                    +"&dni="+edDNIRegistro.getText().toString()
                    +"&movil="+edMovilRegistro.getText().toString()
                    +"&fecha_nacimiento="+edFechaRegistro.getText().toString()
                    +"&genero="+genero2
                    +"&correo="+edCorreoRegistro.getText().toString()
                    +"&contrasenya="+entrenador.getContrasenya().toString()
                    +"&id_persona=" + entrenador.getId_persona()
                    +"&id_entrenador=" + entrenador.getId_entrenador();

            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
           VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);




    }

    private boolean comprovarIntegerYString(String s) {

        boolean resultado;

        try {
            Integer.parseInt(s);
            resultado = true;
        }catch(NumberFormatException e){
            resultado = false;
        }

        return resultado;
    }

    @Override
    public void onResponse(JSONObject response) {

        JSONArray json=response.optJSONArray("entrenador");
        JSONObject jsonObject=null;

        try {
            jsonObject = json.getJSONObject(0);
            resultado = (jsonObject.optString("resultado"));

        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (resultado.equals("Null")){
            Toast.makeText(getApplicationContext(), getString(R.string.errorRellCampsObl), Toast.LENGTH_SHORT).show();
        }

        else{
            Toast.makeText(getApplicationContext(), getString(R.string.GuardarCambios), Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), getString(R.string.errorConexionBD), Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());

    }






    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = year + "/" + (month+1) + "/" + day;
                edFechaRegistro.setText(selectedDate);
            }
        });
        newFragment.show(getFragmentManager(), "dataPicker");
    }
}

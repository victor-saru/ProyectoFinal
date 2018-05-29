package com.example.victo.coachmanager;

import android.app.DatePickerDialog;
import android.app.DownloadManager;
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
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener{

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
    String genero;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        edCorreoRegistro = (EditText) findViewById(R.id.edCorreoRegistro);
        edPasswordRegistro = (EditText) findViewById(R.id.edPasswordRegistro);
        edNombreRegistro = (EditText) findViewById(R.id.edNombreRegistro);
        edPrimerApellidoRegistro = (EditText) findViewById(R.id.edPrimerApellidoRegistro);
        edSegundoApellidoRegistro = (EditText) findViewById(R.id.edSegundoApellidoRegistro);
        edDNIRegistro = (EditText) findViewById(R.id.edDNIRegistro);
        edMovilRegistro = (EditText) findViewById(R.id.edMovilRegistro);
        edFechaRegistro = (EditText) findViewById(R.id.edFechaRegistro);
        edFechaRegistro.setOnClickListener(this);
        btnRegistrarse = (Button) findViewById(R.id.btnRegistrar);
        spinnerGeneroRegistro = (Spinner) findViewById(R.id.spinnerGeneroRegistro);
        
        request = Volley.newRequestQueue(getApplicationContext());

        String[] Generos = {" ", getString(R.string.GeneroMasculino), getString(R.string.GeneroFemenino)};

        spinnerGeneroRegistro.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Generos));

        btnRegistrarse.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.edFechaRegistro:{
                showDatePickerDialog();
                break;
            }
            
            case R.id.btnRegistrar:{
                comprobarValores();
                break;
            }
        }
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
                genero = "Masculino";
            else if (spinnerGeneroRegistro.getSelectedItem().toString().equals(" "))
                genero = " ";
            else
                genero = "Femenino";

            cargarWebService();
        }

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

    private void cargarWebService() {

        String url="http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_InsertPersona_Entrenador.php?nombre="+edNombreRegistro.getText().toString()
                +"&primer_apellido="+edPrimerApellidoRegistro.getText().toString()
                +"&segundo_apellido="+edSegundoApellidoRegistro.getText().toString()
                +"&dni="+edDNIRegistro.getText().toString()
                +"&movil="+edMovilRegistro.getText().toString()
                +"&fecha_nacimiento="+edFechaRegistro.getText().toString()
                +"&genero="+genero
                +"&correo="+edCorreoRegistro.getText().toString()
                +"&contrasenya="+edPasswordRegistro.getText().toString();




        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    private void showDatePickerDialog() { //calendario
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

    @Override
    public void onResponse(JSONObject response) {

        JSONArray json=response.optJSONArray("persona");
        JSONObject jsonObject=null;
        try {
            jsonObject = json.getJSONObject(0);
            resultado = (jsonObject.optString("resultado"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(resultado.equals("CorreoRepetido")){
            Toast.makeText(getApplicationContext(), getString(R.string.errorCorreoExistente), Toast.LENGTH_SHORT).show();
        }

        else if(resultado.equals("DniRepetido")){
            Toast.makeText(getApplicationContext(), getString(R.string.errorDNIExistente), Toast.LENGTH_SHORT).show();
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

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
    JSONObject jsonObject;

    String[] Generos = {" ", "Femenino", "Masculino"};

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
                cargarWebService();
                break;
            }
        }
    }

    private void cargarWebService() {

        String url="http://10.1.6.74/CoachManagerPHP/CoachManager_InsertPersona.php?nombre="+edNombreRegistro.getText().toString()
                +"&primer_apellido="+edPrimerApellidoRegistro.getText().toString()
                +"&segundo_apellido="+edSegundoApellidoRegistro.getText().toString()
                +"&dni="+edDNIRegistro.getText().toString()
                +"&movil="+edMovilRegistro.getText().toString()
                +"&fecha_nacimiento="+edFechaRegistro.getText().toString()
                +"&genero="+spinnerGeneroRegistro.getSelectedItem().toString();


        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    private void showDatePickerDialog() { //calendario
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                edFechaRegistro.setText(selectedDate);
            }
        });
        newFragment.show(getFragmentManager(), "dataPicker");
    }

    @Override
    public void onResponse(JSONObject response) {


        try {
            JSONArray json = response.optJSONArray("persona");
            jsonObject = json.getJSONObject(0);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("kk", "HOLAAAAA");
        System.out.println(jsonObject.optString("persona"));

        Log.e("kk", jsonObject.optString("persona"));

        if(jsonObject.optString("dni") == "Repetido"){
            Toast.makeText(getApplicationContext(), "DNI Repetido", Toast.LENGTH_SHORT).show();
        }

        if(jsonObject.optString("dni") == "Null"){
            Toast.makeText(getApplicationContext(), "Tienes q rellenar los campos obligatorios", Toast.LENGTH_SHORT).show();
        }

        else{
            Toast.makeText(getApplicationContext(), "Registrado", Toast.LENGTH_SHORT).show();
            edCorreoRegistro.setText("");
            edPasswordRegistro.setText("");
            edNombreRegistro.setText("");
            edPrimerApellidoRegistro.setText("");
            edSegundoApellidoRegistro.setText("");
            edDNIRegistro.setText("");
            edMovilRegistro.setText("");
            edFechaRegistro.setText("");
            spinnerGeneroRegistro.setSelection(0);
        }

    }
    
    @Override
    public void onErrorResponse(VolleyError error) {

        Log.e("kk", error.getMessage());
        Toast.makeText(getApplicationContext(), "No se ha registrado correctamnte", Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());
    }

   
}

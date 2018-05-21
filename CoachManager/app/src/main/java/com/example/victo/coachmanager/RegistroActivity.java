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
            Toast.makeText(getApplicationContext(), "Tienes que rellenar todos los campos obligatorios", Toast.LENGTH_SHORT).show();
        }

        else if(!comprovarIntegerYString(edMovilRegistro.getText().toString())){
            Toast.makeText(getApplicationContext(), "Tienes que introducir un móvil correcto", Toast.LENGTH_SHORT).show();
        }

        else if(comprovarIntegerYString(edNombreRegistro.getText().toString())){
            Toast.makeText(getApplicationContext(), "Tienes que introducir un nombre correcto", Toast.LENGTH_SHORT).show();
        }

        else if(comprovarIntegerYString(edPrimerApellidoRegistro.getText().toString())){
            Toast.makeText(getApplicationContext(), "Tienes que introducir un apellido correcto", Toast.LENGTH_SHORT).show();
        }

        else if(comprovarIntegerYString(edSegundoApellidoRegistro.getText().toString())){
            Toast.makeText(getApplicationContext(), "Tienes que introducir un apellido correcto", Toast.LENGTH_SHORT).show();
        }

        else{
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

        String url="http://10.1.6.23/CoachManagerPHP/CoachManager_InsertPersona_Entrenador.php?nombre="+edNombreRegistro.getText().toString()
                +"&primer_apellido="+edPrimerApellidoRegistro.getText().toString()
                +"&segundo_apellido="+edSegundoApellidoRegistro.getText().toString()
                +"&dni="+edDNIRegistro.getText().toString()
                +"&movil="+edMovilRegistro.getText().toString()
                +"&fecha_nacimiento="+edFechaRegistro.getText().toString()
                +"&genero="+spinnerGeneroRegistro.getSelectedItem().toString()
                +"&correo="+edCorreoRegistro.getText().toString()
                +"&contrasenya="+edPasswordRegistro.getText().toString();

                System.out.println("CONTRASEÑA: " + edPasswordRegistro.getText().toString());
        //http://10.1.6.74/CoachManagerPHP/CoachManager_InsertPersona.php?nombre=Victor&primer_apellido=Sanchez&segundo_apellido=Rubio&dni=134&movil=661837375&fecha_nacimiento=2018/05/03&genero=masculino&correo=victor&contrasenya=asdf



        System.out.println("FECHA NACIMIENTO: " + edFechaRegistro.getText().toString());

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
            Toast.makeText(getApplicationContext(), "Has introducido un correo ya existente", Toast.LENGTH_SHORT).show();
        }

        else if(resultado.equals("DniRepetido")){
            Toast.makeText(getApplicationContext(), "Has introducido un DNI ya existente", Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(), "Has introducido un DNI ya existente", Toast.LENGTH_SHORT).show(); CATALAN
            //Toast.makeText(getApplicationContext(), "Has introducido un DNI ya existente", Toast.LENGTH_SHORT).show(); INGLES
        }

        else if(resultado.equals("Null")){
            Toast.makeText(getApplicationContext(), "Tienes que rellenar todos los campos obligatorios", Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(), "Tienes que rellenar todos los campos obligatorios", Toast.LENGTH_SHORT).show(); CATALAN
            //Toast.makeText(getApplicationContext(), "Tienes que rellenar todos los campos obligatorios", Toast.LENGTH_SHORT).show(); INGLES
        }

        else{
            Toast.makeText(getApplicationContext(), "Registrado con éxito", Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(), "Registrado con éxito", Toast.LENGTH_SHORT).show(); CATALAN
            //Toast.makeText(getApplicationContext(), "Registrado con éxito", Toast.LENGTH_SHORT).show(); INGLES
            finish();
        }

    }
    
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "No se ha podido conectar con la base de datos", Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());
    }

   
}

package com.example.victo.coachmanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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

public class AnyadirAlumnoActivity extends AppCompatActivity implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener{

    EditText edNombreAlumno;
    EditText edPrimerApellidoAlumno;
    EditText edSegundoApellidoAlumno;
    Spinner spGeneroAlumno;
    EditText edFechaNacimientoAlumno;
    EditText edDNIAlumno;
    EditText edMovilAlumno;
    EditText edPesoAlumno;
    EditText edAlturaAlumno;
    Spinner spManoDomAlumno;
    Spinner spPieDomAlumno;
    EditText edObservacionesAlumno;
    Button btnAñadirAlumno;
    String id_persona;

    String resultado;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anyadir_alumno);

        btnAñadirAlumno = (Button) findViewById(R.id.btnAñadirAlumno);
        edNombreAlumno = (EditText) findViewById(R.id.edNombreAlumno);
        edPrimerApellidoAlumno = (EditText) findViewById(R.id.edPrimerApellidoAlumno);
        edSegundoApellidoAlumno = (EditText) findViewById(R.id.edSegundoApellidoAlumno);
        spGeneroAlumno = (Spinner) findViewById(R.id.SpGeneroAlumno);
        edFechaNacimientoAlumno = (EditText) findViewById(R.id.edFechaNacimientoAlumno);
        edDNIAlumno = (EditText) findViewById(R.id.edDNIAlumno);
        edMovilAlumno = (EditText) findViewById(R.id.edMovilAlumno);
        edPesoAlumno = (EditText) findViewById(R.id.edPesoAlumno);
        edAlturaAlumno = (EditText) findViewById(R.id.edAlturaAlumno);
        spManoDomAlumno = (Spinner) findViewById(R.id.SpManoDom);
        spPieDomAlumno = (Spinner) findViewById(R.id.SpPieDom);
        edObservacionesAlumno = (EditText) findViewById(R.id.edObservacionesAlumno);

        request = Volley.newRequestQueue(getApplicationContext());

        edFechaNacimientoAlumno.setOnClickListener(this);
        btnAñadirAlumno.setOnClickListener(this);

        Bundle objecteEnviat = getIntent().getExtras();

        if(objecteEnviat != null){
            id_persona = (String) objecteEnviat.getSerializable("id_persona");
        }

        id_persona = "29";


        System.out.println("ID PERSONA LOGEADA: " + id_persona);

        ImageView btnVolver = (ImageView) findViewById(R.id.btnVolver);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnyadirAlumnoActivity.this, AlumnosActivity.class);
                startActivityForResult(intent,1);
            }
        });

        Spinner SpGeneroAlumno = (Spinner) findViewById(R.id.SpGeneroAlumno);
        String[] GeneroAlumno = {" ", getString(R.string.GeneroMasculino), getString(R.string.GeneroFemenino)};
        SpGeneroAlumno.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, GeneroAlumno));

        Spinner SpManoDom = (Spinner) findViewById(R.id.SpManoDom);
        String[] ManoDom = {" ", getString(R.string.ManoDomIzq), getString(R.string.ManoDomDer)};
        SpManoDom.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ManoDom));

        Spinner SpPieDom = (Spinner) findViewById(R.id.SpPieDom);
        String[] PieDom = {" ", getString(R.string.PieDomIzq), getString(R.string.PieDomDer)};
        SpPieDom.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, PieDom));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.edFechaNacimientoAlumno:{
                showDatePickerDialog();
                break;
            }

            case R.id.btnAñadirAlumno:{
                comprobarValores();
                //Intent intent = new Intent(AnyadirAlumnoActivity.this, AlumnosActivity.class);
                //StartActivityForResult(intent,1);
                break;
            }
        }
    }

    private void comprobarValores() {

        if(edMovilAlumno.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Tienes que rellenar todos los campos obligatorios", Toast.LENGTH_SHORT).show();
        }

        else if(!comprovarIntegerYString(edMovilAlumno.getText().toString())){
            Toast.makeText(getApplicationContext(), "Tienes que introducir un móvil correcto", Toast.LENGTH_SHORT).show();
        }

        else if(edObservacionesAlumno.getText().toString().length() > 43){
            Toast.makeText(getApplicationContext(), "Límite de caracteres alcanzado en observaciones", Toast.LENGTH_SHORT).show();
        }

        else if(!comprovarIntegerYString(edPesoAlumno.getText().toString())){
            Toast.makeText(getApplicationContext(), "Tienes que introducir un peso correcto", Toast.LENGTH_SHORT).show();
        }

        else if(!comprovarIntegerYString(edAlturaAlumno.getText().toString())){
            Toast.makeText(getApplicationContext(), "Tienes que introducir una altura correcta", Toast.LENGTH_SHORT).show();
        }

        else if(comprovarIntegerYString(edNombreAlumno.getText().toString())){
            Toast.makeText(getApplicationContext(), "Tienes que introducir un nombre correcto", Toast.LENGTH_SHORT).show();
        }

        else if(comprovarIntegerYString(edPrimerApellidoAlumno.getText().toString())){
            Toast.makeText(getApplicationContext(), "Tienes que introducir un apellido correcto", Toast.LENGTH_SHORT).show();
        }


        else if(comprovarIntegerYString(edSegundoApellidoAlumno.getText().toString())){
            Toast.makeText(getApplicationContext(), "Tienes que introducir un apellido correcto", Toast.LENGTH_SHORT).show();
        }

        else{
            cargarWebService();
        }
    }

    private void cargarWebService() {

        String url = "http://10.1.6.74/CoachManagerPHP/CoachManager_InsertAlumno.php?nombre="+edNombreAlumno.getText().toString()
                +"&primer_apellido="+ edPrimerApellidoAlumno.getText().toString()
                +"&segundo_apellido="+ edSegundoApellidoAlumno.getText().toString()
                +"&dni=" +edDNIAlumno.getText().toString()
                +"&movil="+ edMovilAlumno.getText().toString()
                +"&fecha_nacimiento="+ edFechaNacimientoAlumno.getText().toString()
                +"&genero=" +spGeneroAlumno.getSelectedItem().toString()
                +"&peso=" +edPesoAlumno.getText().toString()
                +"&altura="+ edAlturaAlumno.getText().toString()
                +"&mano_dom="+ spManoDomAlumno.getSelectedItem().toString()
                +"&pie_dom=" +spPieDomAlumno.getSelectedItem().toString()
                +"&observaciones="+ edObservacionesAlumno.getText().toString()
                +"&id_entrenador_entrenador=" + id_persona;



        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);

    }

    private void showDatePickerDialog() { //calendario
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = year + "/" + (month+1) + "/" + day;
                edFechaNacimientoAlumno.setText(selectedDate);
            }
        });
        newFragment.show(getFragmentManager(), "dataPicker");
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
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

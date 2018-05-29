package com.example.victo.coachmanager;

import android.app.DatePickerDialog;
import android.content.Intent;
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
    String id_personaLogeada;
    String mano, pie, genero;
    String id_alumno, id_persona;

    String resultado;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anyadir_alumno);

        btnAñadirAlumno = (Button) findViewById(R.id.btnAñadirAlumno);
        edNombreAlumno = (EditText) findViewById(R.id.edNombreAlumnoAñadir);
        edPrimerApellidoAlumno = (EditText) findViewById(R.id.edPrimApeAlumnoAñadir);
        edSegundoApellidoAlumno = (EditText) findViewById(R.id.edSegApeAlumnoAñadir);
        spGeneroAlumno = (Spinner) findViewById(R.id.SpGeneroAlumnoAñadir);
        edFechaNacimientoAlumno = (EditText) findViewById(R.id.edFechaAlumnoAñadir);
        edDNIAlumno = (EditText) findViewById(R.id.edDNIAlumnoAñadir);
        edMovilAlumno = (EditText) findViewById(R.id.edMovilAlumnoAñadir);
        edPesoAlumno = (EditText) findViewById(R.id.edPesoAlumnoAñadir);
        edAlturaAlumno = (EditText) findViewById(R.id.edAlturaAlumnoAñadir);
        spManoDomAlumno = (Spinner) findViewById(R.id.SpManoDomAlumnoAñadir);
        spPieDomAlumno = (Spinner) findViewById(R.id.SpPieDomAlumnoAñadir);
        edObservacionesAlumno = (EditText) findViewById(R.id.edObservAlumnoAñadir);

        request = Volley.newRequestQueue(getApplicationContext());

        edFechaNacimientoAlumno.setOnClickListener(this);
        btnAñadirAlumno.setOnClickListener(this);


        id_personaLogeada = ((ObtenerIDs) this.getApplication()).getId_persona_Logeada();

        ImageView btnVolver = (ImageView) findViewById(R.id.btnVolverAñadirAlumno);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Spinner SpGeneroAlumno = (Spinner) findViewById(R.id.SpGeneroAlumnoAñadir);
        String[] GeneroAlumno = {" ", getString(R.string.GeneroMasculino), getString(R.string.GeneroFemenino)};
        SpGeneroAlumno.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, GeneroAlumno));

        Spinner SpManoDom = (Spinner) findViewById(R.id.SpManoDomAlumnoAñadir);
        String[] ManoDom = {" ", getString(R.string.ManoDomIzq), getString(R.string.ManoDomDer)};
        SpManoDom.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ManoDom));

        Spinner SpPieDom = (Spinner) findViewById(R.id.SpPieDomAlumnoAñadir);
        String[] PieDom = {" ", getString(R.string.PieDomIzq), getString(R.string.PieDomDer)};
        SpPieDom.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, PieDom));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.edFechaAlumnoAñadir:{
                showDatePickerDialog();
                break;
            }

            case R.id.btnAñadirAlumno:{
                comprobarValores();

                break;
            }
        }
    }

    private void comprobarValores() {

        if(edMovilAlumno.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), getString(R.string.errorRellCampsObl), Toast.LENGTH_SHORT).show();
        }

        else if(!comprovarIntegerYString(edMovilAlumno.getText().toString())){
            Toast.makeText(getApplicationContext(), getString(R.string.errorMovil), Toast.LENGTH_SHORT).show();
        }

        else if(edObservacionesAlumno.getText().toString().length() > 43){
            Toast.makeText(getApplicationContext(), "Límite de caracteres alcanzado en observaciones", Toast.LENGTH_SHORT).show();
        }

        else if(!comprovarIntegerYString(edPesoAlumno.getText().toString())){
            Toast.makeText(getApplicationContext(), getString(R.string.errorPeso), Toast.LENGTH_SHORT).show();
        }

        else if(!comprovarIntegerYString(edAlturaAlumno.getText().toString())){
            Toast.makeText(getApplicationContext(), getString(R.string.errorAltura), Toast.LENGTH_SHORT).show();
        }

        else if(comprovarIntegerYString(edNombreAlumno.getText().toString())){
            Toast.makeText(getApplicationContext(), getString(R.string.errorNombre), Toast.LENGTH_SHORT).show();
        }

        else if(comprovarIntegerYString(edPrimerApellidoAlumno.getText().toString())){
            Toast.makeText(getApplicationContext(), getString(R.string.errorApellido), Toast.LENGTH_SHORT).show();
        }

        else if(comprovarIntegerYString(edSegundoApellidoAlumno.getText().toString())){
            Toast.makeText(getApplicationContext(), getString(R.string.errorApellido), Toast.LENGTH_SHORT).show();
        }

        else{

            if(spGeneroAlumno.getSelectedItem().toString().equals("Masculino") || spGeneroAlumno.getSelectedItem().toString().equals("Masculí") || spGeneroAlumno.getSelectedItem().toString().equals("Male") )
                genero = "Masculino";
            else if(spGeneroAlumno.getSelectedItem().toString().equals(" "))
                genero = "";

            else
                genero = "Femenino";

            if(spManoDomAlumno.getSelectedItem().toString().equals("Derecha") || spManoDomAlumno.getSelectedItem().toString().equals("Dreta") || spManoDomAlumno.getSelectedItem().toString().equals("Right"))
                mano = "Derecha";

            else if(spManoDomAlumno.getSelectedItem().toString().equals(" "))
                mano = " ";

            else{
                mano = "Izquierda";
            }

            if(spPieDomAlumno.getSelectedItem().toString().equals("Derecha") || spManoDomAlumno.getSelectedItem().toString().equals("Dreta") || spManoDomAlumno.getSelectedItem().toString().equals("Right"))
                pie = "Derecho";

            else if(spPieDomAlumno.getSelectedItem().toString().equals(" "))
                pie = " ";

            else{
                pie = "Izquierda";
            }

            cargarWebService();
        }
    }

    private void cargarWebService() {



        String url = "http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_InsertAlumno.php?nombre="+edNombreAlumno.getText().toString()
                +"&primer_apellido="+ edPrimerApellidoAlumno.getText().toString()
                +"&segundo_apellido="+ edSegundoApellidoAlumno.getText().toString()
                +"&dni=" +edDNIAlumno.getText().toString()
                +"&movil="+ edMovilAlumno.getText().toString()
                +"&fecha_nacimiento="+ edFechaNacimientoAlumno.getText().toString()
                +"&genero=" +genero
                +"&peso=" +edPesoAlumno.getText().toString()
                +"&altura="+ edAlturaAlumno.getText().toString()
                +"&mano_dom="+mano
                +"&pie_dom=" +pie
                +"&observaciones="+ edObservacionesAlumno.getText().toString()
                +"&id_persona_entrenador=" + id_personaLogeada;



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
            Toast.makeText(getApplicationContext(), getString(R.string.errorCorreoExistente), Toast.LENGTH_SHORT).show();
        }

        else if(resultado.equals("DniRepetido")){
            Toast.makeText(getApplicationContext(), getString(R.string.errorDNIExistente), Toast.LENGTH_SHORT).show();
        }


        else if(resultado.equals("Null")){
            Toast.makeText(getApplicationContext(), getString(R.string.errorRellCampsObl), Toast.LENGTH_SHORT).show();
        }

        else{


            id_alumno = resultado;

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

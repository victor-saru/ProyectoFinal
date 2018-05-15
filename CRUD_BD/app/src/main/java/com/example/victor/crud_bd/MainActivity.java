package com.example.victor.crud_bd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.sql.Connection;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    EditText edNombre;
    EditText edApellido;
    Button btnRegistrar;
    ProgressDialog progreso;
    Button btnConsulta;
    
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edNombre = (EditText) findViewById(R.id.edNombre);
        edApellido = (EditText) findViewById(R.id.edApellido);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        btnConsulta = (Button) findViewById(R.id.btnConsulta);

        request = Volley.newRequestQueue(getApplicationContext());

        btnConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ConsultaActivity.class);
                startActivity(i);;
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarWebService();
            }


        });

    }

    private void cargarWebService() {

        /*progreso = new ProgressDialog(getApplicationContext());
        progreso.setMessage("Prrrrrrrrrcés");
        progreso.show();*/

        String url="http://10.1.6.74/ejemploBDremota/wsJSONMRegistro.php?nombre="+edNombre.getText().toString()+"&apellido="+edApellido.getText().toString();

        // url = url.replace("", "%20") donde encuentre espacio que inserte un espacio real sin problemas

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) { //si en la comunicacion es correta

        Toast.makeText(getApplicationContext(), "Se ha registrado correctamnte", Toast.LENGTH_SHORT).show();
        //progreso.hide();
        edNombre.setText("");
        edApellido.setText("");

    }

    @Override
    public void onErrorResponse(VolleyError error) { //si falla algo en la comunicacion

        Toast.makeText(getApplicationContext(), "No se ha registrado correctamnte", Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());
        //progreso.hide();
    }


}

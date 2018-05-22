package com.example.victo.coachmanager;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener{

    private Button btnRegistrase;
    private Button btnLogin;
    private Button btnRecordarPassword;
    private EditText edCorreoLogin;
    private EditText edPasswordLogin;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    public String resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnRegistrase = (Button) (findViewById(R.id.btnRegistrase));
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRecordarPassword = (Button) findViewById(R.id.btnRecordarPassword);
        edCorreoLogin = (EditText) findViewById(R.id.edCorreoLogin);
        edPasswordLogin = (EditText) findViewById(R.id.edPasswordLogin);


        request = Volley.newRequestQueue(getApplicationContext());

        btnRegistrase.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnRecordarPassword.setOnClickListener(this);

    }




    @Override
    public void onClick(View view) {

        int id = view.getId();
        Log.e("kk", "2");

        if(id == btnRegistrase.getId()){
            Log.e("kk", "3");
            Intent i = new Intent(this, RegistroActivity.class);
            startActivity(i);
        }

        else if(id == btnLogin.getId()){

            cargarWebSerevice();

        }

        else if(id == btnRecordarPassword.getId()){
            Intent i = new Intent(this, OlvidarContrasenyaActivity.class);
            startActivity(i);
        }

    }

    private void cargarWebSerevice() {

        String url="http://10.1.6.74/CoachManagerPHP/CoachManager_Login.php?"
                +"correo="+edCorreoLogin.getText().toString()
                +"&contrasenya="+edPasswordLogin.getText().toString();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);

    }

    @Override
    public void onResponse(JSONObject response) {

        JSONArray json=response.optJSONArray("login");
        JSONObject jsonObject=null;

        try {
            jsonObject = json.getJSONObject(0);
            resultado = (jsonObject.optString("resultado"));
            ((IdPersonaLogeada) this.getApplication()).setId_persona(resultado);
        }catch(JSONException e){
            e.printStackTrace();
        }

        if(resultado.equals("Null")){
            Toast.makeText(getApplicationContext(), getString(R.string.errorRellCampsObl), Toast.LENGTH_SHORT).show();
        }

        else if(resultado.equals("Correo")){
            Toast.makeText(getApplicationContext(), getString(R.string.errorCorreo), Toast.LENGTH_SHORT).show();
        }

        else if(resultado.equals("Contrasenya")){
            Toast.makeText(getApplicationContext(), getString(R.string.errorContrasenya), Toast.LENGTH_SHORT).show();
        }

        else{
            Toast.makeText(getApplicationContext(), getString(R.string.InicioSesionCorrecto), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, MenuActivity.class);
            Intent intent2 = new Intent(this, AnyadirAlumnoActivity.class);

            System.out.println("RESULTADO: " + resultado);


            startActivityForResult(intent,1);

        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), getString(R.string.errorConexionBD), Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());
    }


}







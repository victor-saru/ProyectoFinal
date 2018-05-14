package com.example.victor.crud_bd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.victor.crud_bd.Entidades.Usuarios;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ConsultarListaActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    //objetos de la clase que no voy a poner

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_lista);

        request = Volley.newRequestQueue(getApplicationContext());

        cargarWebService();
    }

    private void cargarWebService() {

        String url ="http://192.168.1.45/ejemploBDremota/wsJSONMLista.php";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }


    @Override
    public void onResponse(JSONObject response) {

        Usuarios u1 = new Usuarios();

        JSONArray json = response.optJSONArray("usuarios");

        try {
            for (int i = 0; i < json.length(); i++){
                u1 = new Usuarios();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);

                u1.setNombre(jsonObject.optString("nombre"));
                u1.setApellido(jsonObject.optString("apellido"));

                //añadir a la lista del LISTVIEW el usuario u1
            }

            //adaptador

        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getApplicationContext(), "No se ha encontrado ningun usuario", Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());
        //progreso.hide();

    }


}

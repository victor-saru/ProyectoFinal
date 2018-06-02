package com.example.victo.coachmanager;

import android.support.v7.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.victo.coachmanager.Adapters.AdapterDeporte;
import com.example.victo.coachmanager.Entidades.Deporte;
import com.example.victo.coachmanager.Entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by VICTOR on 02/06/2018.
 */

public class ObtenerDeporte extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {


    ArrayList<Deporte> deportes;

    public ObtenerDeporte() {
    }

    public ArrayList<Deporte> getDeportes() {
        return deportes;
    }

    public void cargarWebService(){

        String url = "http://192.168.1.45/CoachManagerPHP/CoachManager_Deportes.php";


        System.out.println(url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

        JSONArray json = response.optJSONArray("deportes");
        JSONObject jsonObjectDeportes=null;

        try {
            jsonObjectDeportes = json.getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String resultado = (jsonObjectDeportes.optString("resultado"));

        if(!resultado.equals("Null")){
            deportes.removeAll(deportes);

            try {

                for(int i = 0; i < json.length(); i++){
                    Deporte d = new Deporte();
                    JSONObject jsonObject = null;
                    jsonObject=json.getJSONObject(i);
                    d.setId_deporte(jsonObject.optInt("id_deporte"));
                    d.setNombre(jsonObject.optString("nombre"));


                    deportes.add(d);
                    System.out.println(d.getNombre());


                }

            }catch(JSONException e){
                e.printStackTrace();
            }



        }

    }
}

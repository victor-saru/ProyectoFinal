package com.example.victor.crud_bd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class ConsultaActivity extends AppCompatActivity implements Response.Listener<JSONObject>,
        Response.ErrorListener{

    EditText edBusqueda;
    Button btnBuscar;
    TextView lblNombre;
    TextView lblApellido;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        edBusqueda = (EditText) findViewById(R.id.edBusqueda);
        btnBuscar = (Button) findViewById(R.id.btnBuscar);
        lblNombre = (TextView) findViewById(R.id.lblNombre);
        lblApellido = (TextView) findViewById(R.id.lblApellido);

        request = Volley.newRequestQueue(getApplicationContext());

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarWebService();
            }
        });
    }

    private void cargarWebService() {

        String url = "http://192.168.1.45/ejemploBDremota/wsJSONMConsulta.php?nombre="
                + edBusqueda.getText().toString();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getApplicationContext(), "Se ha encontrado el usuario", Toast.LENGTH_SHORT).show();
        //progreso.hide();

        Usuarios u1 = new Usuarios();

        JSONArray json = response.optJSONArray("usuarios");
        JSONObject jsonObject = null;

        try {
            jsonObject = json.getJSONObject(0);
            u1.setNombre(jsonObject.optString("nombre"));
            u1.setApellido(jsonObject.optString("apellido"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        lblNombre.setText(u1.getNombre());
        lblApellido.setText(u1.getApellido());

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "No se ha encontrado ningun usuario", Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());
        //progreso.hide();
    }


}

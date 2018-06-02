package com.example.victo.coachmanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.victo.coachmanager.Entidades.Deporte;
import com.example.victo.coachmanager.Entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener , Response.Listener<JSONObject>, Response.ErrorListener{

    private String id_persona;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    String resultado;
    TextView lblNombreApellidosBar;
    TextView lblCorreoBar;
    public static ArrayList<Deporte> deportes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        //setContentView(R.layout.nav_header_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        id_persona = ((ObtenerIDs) this.getApplication()).getId_persona_Logeada();

        deportes = new ArrayList<>();

        cargarWebService();
        cargarWebServiceDeporte();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button btnSobreNosotros = (Button) findViewById(R.id.btnSobreNosotros);

        btnSobreNosotros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, SobreNosotrosActivity.class));
            }
        });

        ImageButton btnAlumnos = (ImageButton) findViewById(R.id.btnAlumnos);

        btnAlumnos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, AlumnosActivity.class));
            }
        });

        ImageButton btnGrupos = (ImageButton) findViewById(R.id.btnGrupos);

        btnGrupos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, GruposActivity.class));
            }
        });

        ImageButton btnEjercicios = (ImageButton) findViewById(R.id.btnEjercicios);

        btnEjercicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, EjerciciosActivity.class));
            }
        });

        ImageButton btnEntrenamientos = (ImageButton) findViewById(R.id.btnEntrenamiento);

        btnEntrenamientos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, EntrenamientosActivity.class));
            }
        });

    }

    private void cargarWebServiceDeporte() {

        String url = "http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_Deportes.php";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);

    }

    private void cargarWebService() {

        String url="http://"+((ObtenerIDs) this.getApplication()).getIp()+"/CoachManagerPHP/CoachManager_InfoPersona.php?id_persona="+id_persona.toString();


        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.ajustesUsuario) {

        } else if (id == R.id.cerrarSesion) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onResponse(JSONObject response) {

        JSONArray json = response.optJSONArray("persona");

        JSONArray jsonDeportes = response.optJSONArray("deportes");
        JSONObject jsonObjectDeportes=null;

        if(jsonDeportes != null){
            try {
                jsonObjectDeportes = jsonDeportes.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String resultado = (jsonObjectDeportes.optString("resultado"));

            if(!resultado.equals("Null")){
                deportes.removeAll(deportes);

                try {

                    for(int i = 0; i < jsonDeportes.length(); i++){
                        Deporte d = new Deporte();
                        JSONObject jsonObject = null;
                        jsonObject=jsonDeportes.getJSONObject(i);
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

        if(json != null){
            try {
                String nombre = null;
                String apellidos = null;
                String correo = null;

                for(int i = 0; i < json.length(); i++){
                    JSONObject jsonObject = null;
                    jsonObject = json.getJSONObject(i);
                    nombre = jsonObject.optString("nombre");
                    apellidos = jsonObject.optString("primer_apellido");
                    apellidos += " " + jsonObject.optString("segundo_apellido");
                    correo = jsonObject.optString("CORREO");
                }

                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

                View hView = navigationView.getHeaderView(0);

                lblNombreApellidosBar = (TextView) hView.findViewById(R.id.lblNombreApellidosBar);
                lblCorreoBar = (TextView) hView.findViewById(R.id.lblCorreoBar);
                lblNombreApellidosBar.setText(nombre + " " + apellidos);
                lblCorreoBar.setText(correo);

            }catch (JSONException e){
                e.printStackTrace();
            }
        }



    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), getString(R.string.errorConexionBD), Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());
    }


}

package com.example.victo.coachmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.victo.coachmanager.Entidades.VolleySingleton;

public class OlvidarContrasenyaActivity extends AppCompatActivity {

    EditText edCorreuRecu;
    Button btnEnviarEnlace;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvidar_contrasenya);

        edCorreuRecu = (EditText)findViewById(R.id.edCorreoRecu);
        btnEnviarEnlace = (Button) findViewById(R.id.btnEnviarEnlace);

        ImageView btnVolver = (ImageView) findViewById(R.id.btnVolver);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OlvidarContrasenyaActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnEnviarEnlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = edCorreuRecu.getText().toString();

                if(!email.isEmpty()){

                    /*String url="http://10.1.6.23/CoachManagerPHP/CoachManager_InfoPersona.php?id_persona="+id_persona;

                    jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
                    VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);*/

                    Intent intent = new Intent(OlvidarContrasenyaActivity.this, MainActivity.class);
                    startActivity(intent);

                }

                else
                    Toast.makeText(getApplicationContext(), getString(R.string.errorEmailVacio), Toast.LENGTH_SHORT).show();


            }
        });

    }
}

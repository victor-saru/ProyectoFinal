package com.example.victo.coachmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;



public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnRegistrase;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnRegistrase = (Button) (findViewById(R.id.btnRegistrase));
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin = (Button) (findViewById(R.id.btnLogin));

        btnRegistrase.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

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
            Intent i = new Intent(this, MenuActivity.class);
            startActivity(i);
        }

    }
}







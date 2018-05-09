package com.example.victo.coachmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnRegistrase;
    private Button btnLogin;
    private Connection conexion;
    private String URL = "jdbc:mysql://localhost:3306/sakila";
    private String IP = "127.0.0.1";
    private int PUERTO = 3306;
    private String BD = "coachmanager";
    private String USER = "root";
    private String PASSWORD = "";
    private Statement statement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegistrase = (Button) (findViewById(R.id.btnRegistrase));
        conexion = null;
        btnLogin = (Button) findViewById(R.id.btnLogin);



        conexionBD();



        btnRegistrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeIntent();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeIntent();
            }
        });

    }

    private void conexionBD() {

        try {
            Class.forName("mysql-connector-java-5.1.28-bin.jar").newInstance ();
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexió a la BD realitzada!");
        }catch(SQLException e){
            System.err.println("Error de connexió amb la BD: " + e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void changeIntent(){
        Intent i = new Intent(this, RegistroActivity.class);
        startActivity(i);
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

        if(id == btnLogin.getId()){
            Intent i = new Intent(this, MenuActivity.class);
            startActivity(i);
        }

    }
}

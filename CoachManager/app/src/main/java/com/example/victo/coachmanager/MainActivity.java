package com.example.victo.coachmanager;

import android.content.Intent;
import android.os.AsyncTask;
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

import static com.example.victo.coachmanager.ConexionBD.prueba;
import static com.example.victo.coachmanager.MainActivity.conn;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnRegistrase;
    private Button btnLogin;
    public static Connection conn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConexionBD a = new ConexionBD();
        a.execute();

       /*Statement st = null;
        try {
            st = (Statement) prueba.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM personas");
            while(rs.next()){
                String resultado = rs.getString("nombre");
                Log.e("kk", resultado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/



        btnRegistrase = (Button) (findViewById(R.id.btnRegistrase));
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin = (Button) (findViewById(R.id.btnLogin));

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




    public void changeIntent(){
        Intent i = new Intent(this, MenuActivity.class);
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

        else if(id == btnLogin.getId()){
            Intent i = new Intent(this, MenuActivity.class);
            startActivity(i);
        }

    }
}


class ConexionBD extends AsyncTask<Void, Integer, Boolean> {

    public static Connection prueba;

    @Override
    protected Boolean doInBackground(Void... voids) {
        conectarBD();
        return null;
    }


    public Connection conectarBD() {


        String url = "jdbc:mysql://192.168.1.45:3306/coachmanager";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "pepe";
        String password = "";

        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url, userName, password);
            prueba = conn;
            Log.e("kk", "Conexión Realizada");


        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error de connexió amb la BD: " + e.getMessage());
        }

        return conn;

    }


}

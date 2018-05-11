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


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnRegistrase;
    private Button btnLogin;
    Connection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conn = null;
       // ConexionBD a = new ConexionBD();
        //a.execute();

        String pepe = "Hola";

        ConexionPrueba a = new ConexionPrueba(pepe);
        Thread tpepe = new Thread(a);
        tpepe.start();

        System.out.println(pepe);

        /*try {
            Statement st = (Statement) conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM personas");
            while(rs.next()){
                String resultado = rs.getString("nombre");
                Log.e("kk", resultado);
            }
        } catch (SQLException e) {
            System.err.println("ERRRROOROROROROOR ");
        }**/

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

class ConexionPrueba implements Runnable{

    Connection conn;
    String pepe;

    public ConexionPrueba(String pepe){
        this.pepe = pepe;
    }

    @Override
    public void run() {

        pepe = "Deu";
        conectar();
    }

    private void conectar() {
        String url = "jdbc:mysql://10.1.6.74:3306/coachmanager";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "pepe";
        String password = "";

        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url, userName, password);
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
    }

    public void ConsultaPrueba(){
        try {
            Statement st = (Statement) conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM personas");
            while(rs.next()){
                String resultado = rs.getString("nombre");
                Log.e("kk", resultado);
            }
        } catch (SQLException e) {
            System.err.println("ERRRROOROROROROOR ");
        }

    }
}


class ConexionBD extends AsyncTask<Void, Integer, Boolean> {

    private static Connection conn;

    @Override
    protected Boolean doInBackground(Void... voids) {
        conectarBD();
        return null;
    }


    public static Connection conectarBD() {

        String url = "jdbc:mysql://10.1.6.74:3306/coachmanager";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "pepe";
        String password = "";

        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url, userName, password);
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

    public void ConsultaPrueba(){
        try {
            Statement st = (Statement) conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM personas");
            while(rs.next()){
                String resultado = rs.getString("nombre");
                Log.e("kk", resultado);
            }
        } catch (SQLException e) {
            System.err.println("ERRRROOROROROROOR ");
        }

    }


}

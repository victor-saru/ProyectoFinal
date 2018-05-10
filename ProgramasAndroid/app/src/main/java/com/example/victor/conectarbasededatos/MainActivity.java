package com.example.victor.conectarbasededatos;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Tarea a = new Tarea();
        a.execute();

    }
}

class Tarea extends AsyncTask<Void, Integer, Boolean>{

    @Override
    protected Boolean doInBackground(Void... voids) {
        conectarBD();
        
        return null;
    }

    private void conectarBD() {

        Connection conn = null;
        String url = "jdbc:mysql://192.168.1.45:3306/coachmanager";
        String dbName = "coachmanager";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "pepe";
        String password = "";
        String resultado;

        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url, userName, password);

            Log.e("kk", "CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCConexion Realizada");

            Statement st = (Statement) conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM personas");
            while(rs.next()){
                resultado = rs.getString("nombre");
                Log.e("kk", resultado);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEError de connexió amb la BD: " + e.getMessage());
        }


    }
}

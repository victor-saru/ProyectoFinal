package com.example.victo.pruebathreads;

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

    //public static Connection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* ConexionClandestina Conexion = new ConexionClandestina();
        Conexion.execute();


        if(ConexionClandestina.rs != null) {
            try {
                while (ConexionClandestina.rs.next()) {
                    String resultado = ConexionClandestina.rs.getString("nombre");
                    Log.e("kk", resultado);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        else{
            System.out.println("Cursor nulo");
        }*/


        Connexion connect = new Connexion();

        ConexionPrueba a = new ConexionPrueba(connect);
        Thread ThreadSaludo = new Thread(a);
        ThreadSaludo.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Nuevo valor: " + connect.prueba);



        System.out.println("aaaaaaaaaaaaaaaaaaaaaaa " + connect.conn.toString());



      /* try {
            Statement st = (Statement) connect.conn.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM personas");
            while(rs.next()){
                String resultado = rs.getString("nombre");
                Log.e("kk", resultado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

    }


/*class ConexionClandestina extends AsyncTask<Void, Integer, Boolean>{

    public static Connection conn;
    public static ResultSet rs;
    @Override
    protected Boolean doInBackground(Void... voids) {

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

        return true;
    }

    @Override
    protected void onPostExecute(Boolean resultado) {
        try {
            Statement st = (Statement) ConexionClandestina.conn.createStatement();
            rs = st.executeQuery("SELECT * FROM personas");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}*/

class Connexion{
    Connection conn = null;
    String prueba = "aa";
}

class ConexionPrueba implements Runnable{

    Connexion connect;


    public ConexionPrueba(Connexion connect){
        this.connect = connect;

    }

    @Override
    public void run() {
        connect.prueba = "bb";
        conectar();
    }

    private void conectar() {
        String url = "jdbc:mysql://192.168.1.45:3306/coachmanager";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "pepe";
        String password = "";

        try {
            Class.forName(driver).newInstance();
            connect.conn = DriverManager.getConnection(url, userName, password);
            System.out.println("bbbbbbbbbbbbbbbbbbbbbbbb " + connect.conn.toString());
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


}

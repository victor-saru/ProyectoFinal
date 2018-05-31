package com.example.oscar.pruebajsonexcel;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    private Button btnConvertir;

    static ArrayList<Alumno> al_alumnos;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        al_alumnos = new ArrayList<Alumno>();

        btnConvertir = (Button) findViewById(R.id.btnConvertir);

        btnConvertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Convertir(MainActivity.this,"myexcel.xls");
            }
        });
    }

    private boolean Convertir(Context context, String fileName) {

        // check if available and not read only
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.i("ErrorSD", "Storage not available or read only");
            return false;
        }

         // Llamamos al WebService/BD
        cargarWebService();

        boolean success = false;

        //New Workbook
        Workbook wb = new HSSFWorkbook();

        Cell c = null;

        //Cell style for header row
        CellStyle cs = wb.createCellStyle();
        cs.setFillForegroundColor(HSSFColor.LIME.index);
        cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        //New Sheet
        Sheet sheet1 = null;
        sheet1 = wb.createSheet("alumnos");

        // Generate column headings
        org.apache.poi.ss.usermodel.Row row = sheet1.createRow(0);

        c = row.createCell(0);
        c.setCellValue("id_alumno");
        c.setCellStyle(cs);

        c = row.createCell(1);
        c.setCellValue("nombre");
        c.setCellStyle(cs);

        c = row.createCell(2);
        c.setCellValue("primer_apellido");
        c.setCellStyle(cs);

        c = row.createCell(3);
        c.setCellValue("segundo_apellido");
        c.setCellStyle(cs);

        c = row.createCell(4);
        c.setCellValue("dni");
        c.setCellStyle(cs);

        c = row.createCell(5);
        c.setCellValue("fecha_nacimiento");
        c.setCellStyle(cs);

        c = row.createCell(6);
        c.setCellValue("genero");
        c.setCellStyle(cs);

        c = row.createCell(7);
        c.setCellValue("mano_dom");
        c.setCellStyle(cs);

        c = row.createCell(8);
        c.setCellValue("pie_dom");
        c.setCellStyle(cs);

        c = row.createCell(9);
        c.setCellValue("observaciones");
        c.setCellStyle(cs);

        c = row.createCell(10);
        c.setCellValue("movil");
        c.setCellStyle(cs);

        c = row.createCell(11);
        c.setCellValue("peso");
        c.setCellStyle(cs);

        c = row.createCell(12);
        c.setCellValue("altura");
        c.setCellStyle(cs);

        c = row.createCell(13);
        c.setCellValue("id_persona");
        c.setCellStyle(cs);

        sheet1.setColumnWidth(0, (15 * 500));
        sheet1.setColumnWidth(1, (15 * 500));
        sheet1.setColumnWidth(2, (15 * 500));

        int columnasdatos = 1;
        int celdasdatos = 0;

        System.out.println(al_alumnos);

        for (Alumno a: al_alumnos) {
            org.apache.poi.ss.usermodel.Row row1 = sheet1.createRow(columnasdatos);
            Cell celula = row1.createCell(celdasdatos);
            celula.setCellValue(a.getId_alumno());
            celdasdatos += 1;

            celula = row1.createCell(celdasdatos);
            celula.setCellValue(a.getNombre());
            celdasdatos += 1;

            celula = row1.createCell(celdasdatos);
            celula.setCellValue(a.getPrimer_apellido());
            celdasdatos += 1;

            celula = row1.createCell(celdasdatos);
            celula.setCellValue(a.getSegundo_apellido());
            celdasdatos += 1;

            celula = row1.createCell(celdasdatos);
            celula.setCellValue(a.getDni());
            celdasdatos += 1;

            celula = row1.createCell(celdasdatos);
            celula.setCellValue(a.getFecha_nacimiento());
            celdasdatos += 1;

            celula = row1.createCell(celdasdatos);
            celula.setCellValue(a.getGenero());
            celdasdatos += 1;

            celula = row1.createCell(celdasdatos);
            celula.setCellValue(a.getMano_dom());
            celdasdatos += 1;

            celula = row1.createCell(celdasdatos);
            celula.setCellValue(a.getPie_dom());
            celdasdatos += 1;

            celula = row1.createCell(celdasdatos);
            celula.setCellValue(a.getObservaciones());
            celdasdatos += 1;

            celula = row1.createCell(celdasdatos);
            celula.setCellValue(a.getMovil());
            celdasdatos += 1;

            celula = row1.createCell(celdasdatos);
            celula.setCellValue(a.getPeso());
            celdasdatos += 1;

            celula = row1.createCell(celdasdatos);
            celula.setCellValue(a.getAltura());
            celdasdatos += 1;

            celula = row1.createCell(celdasdatos);
            celula.setCellValue(a.getId_persona());
            celdasdatos += 1;

            columnasdatos += 1;
            celdasdatos = 0;
        }

        // Create a path where we will place our List of objects on external storage
        //File file = new File("/sdcard", fileName); Clase
        File file = new File(context.getExternalFilesDir(null), fileName); // Casa
        FileOutputStream os = null;

        try {
            os = new FileOutputStream(file);
            wb.write(os);
            Log.w("FileUtils", "Writing file" + file);
            success = true;
        } catch (IOException e) {
            Log.w("FileUtils", "Error writing " + file, e);
        } catch (Exception e) {
            Log.w("FileUtils", "Failed to save file", e);
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception ex) {
            }
        }
        return success;

    }

    private void cargarWebService() {

        String id_entrenador = "2";

        String url="http://192.168.1.168/CoachManagerPHP/CoachManager_Alumnos.php?id_entrenador="+id_entrenador;

        // Esto ejecuta la URL superior y devuelve el resultado de la función PHP
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);

        // Esto te lleva al OnError o OnResponse
        VolleySingleton.getIntanciaVolley(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    @Override
    public void onErrorResponse(VolleyError error) { // Si la llamada al archivo PHP devuelve un JSON erróneo

    }

    @Override
    public void onResponse(JSONObject response) { // Si la llamada al archivo PHP devuelve un JSON correcto

        JSONArray json = response.optJSONArray("alumnos"); // Creamos un JSONArray y lo llenamos con lo recibido en el OnResponse

        JSONObject jsonObject2=null;

        try {
            jsonObject2 = json.getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String resultado = (jsonObject2.optString("resultado"));

        if(!resultado.equals("Null")) {

            al_alumnos.removeAll(al_alumnos);

            for (int i = 0; i < json.length(); i++) {
                Alumno a = new Alumno();
                JSONObject jsonObject = null;
                try {
                    jsonObject = json.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                a.setId_alumno(jsonObject.optInt("id_alumno"));
                a.setNombre(jsonObject.optString("nombre"));
                a.setPrimer_apellido(jsonObject.optString("primer_apellido"));
                a.setSegundo_apellido(jsonObject.optString("segundo_apellido"));
                a.setDni(jsonObject.optString("dni"));
                a.setFecha_nacimiento(jsonObject.optString("fecha_nacimiento"));
                a.setGenero(jsonObject.optString("genero"));
                a.setMano_dom(jsonObject.optString("mano_dom"));
                a.setPie_dom(jsonObject.optString("pie_dom"));
                a.setObservaciones(jsonObject.optString("observaciones"));
                a.setMovil(jsonObject.optInt("movil"));
                a.setPeso(jsonObject.optInt("peso"));
                a.setAltura(jsonObject.optInt("altura"));
                a.setId_persona(jsonObject.optInt("id_persona"));

                al_alumnos.add(a);
            }
        }
    }
}

package com.example.oscar.pruebajsonexcel;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnConvertir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConvertir = (Button) findViewById(R.id.btnConvertir);

        btnConvertir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Convertir(MainActivity.this,"myexcel.xls");
            }
        });
    }

    private static boolean Convertir(Context context, String fileName) {

        System.out.println("Holaaaaaa");

        // check if available and not read only
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.i("ErrorSD", "Storage not available or read only");
            return false;
        }

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

        // Create a path where we will place our List of objects on external storage
        File file = new File(context.getExternalFilesDir(null), fileName);
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
}

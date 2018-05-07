package com.example.victo.coachmanager;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edFechaRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        edFechaRegistro = (EditText) findViewById(R.id.edFechaRegistro);
        edFechaRegistro.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.edFechaRegistro:{
                showDatePickerDialog();
                break;
            }
        }
    }

    private void showDatePickerDialog() { //calendario
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                edFechaRegistro.setText(selectedDate);
            }
        });
        newFragment.show(getFragmentManager(), "dataPicker");
    }
}

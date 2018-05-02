package com.cgaxtr.hiroom.activity;

import android.app.DatePickerDialog;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.cgaxtr.hiroom.R;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class EditProfileActivity extends AppCompatActivity {

    private EditText name, surname, date, city;
    private android.support.design.widget.TextInputLayout birthdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }
        };

        date = findViewById(R.id.editBirthdate);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditProfileActivity.this, dpd, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

    }
}

package com.example.mydailyplannerapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static final int SUBMIT = 1;
    public static final String TAG = "CalendarActivity";
    public static final String DATE = "Date";
    public static final String EXTRA_MESSAGE = "com.example.mydailyplannerapp";

    @SuppressLint("SimpleDateFormat")
    public String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

    CalendarView calendarView;

    //for search
    Button searchButton;
    EditText searchEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       calendarView = (CalendarView) findViewById(R.id.calendarView);


       calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {

               date = year + "/" + month + "/" + day;
           }
       });


    }


    public void addEvent(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        intent.putExtra(DATE, date);
        startActivity(intent);
    }

    public void listOfEvents(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);

    }


}
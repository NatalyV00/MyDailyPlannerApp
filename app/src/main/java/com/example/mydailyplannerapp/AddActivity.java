package com.example.mydailyplannerapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import static com.example.mydailyplannerapp.MainActivity.DATE;

public class AddActivity extends AppCompatActivity {
    TextView header;
    private EventsDbHelper mHelper;
    private String date;
    EditText titleEdit, eventEdit;
    TimePicker timePicker;

    private ScheduleClient scheduleClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        scheduleClient = new ScheduleClient(this);
        scheduleClient.doBindService();

        Intent incoming = getIntent();
        date = incoming.getStringExtra(DATE);
        String headerText = "Event for " + date;
        header = (TextView) findViewById(R.id.textView3);
        header.setText(headerText);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void submitTask(View view) {
         titleEdit = (EditText) findViewById(R.id.title);
         eventEdit = (EditText) findViewById(R.id.description);
        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);

        int day = Integer.parseInt(date.substring(8, 10));
       // int month = Integer.parseInt(date.substring(5, 7)) - 1;
        int month = Integer.parseInt(date.substring(5, 7));
        int year = Integer.parseInt(date.substring(0, 4));
        int hour = timePicker.getHour();
        int minutes = timePicker.getMinute();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minutes);

        scheduleClient.setAlarmForNotification(calendar);
        Toast.makeText(this, "Notification set for: "+ day +"/"+ month +"/"+ year + "-" + hour + ":" + minutes, Toast.LENGTH_SHORT).show();

        mHelper = new EventsDbHelper(this);
        String title = String.valueOf(titleEdit.getText());
        String event = String.valueOf(eventEdit.getText());
        /* time is in the format yyyy/MM/dd-HH:mm */
        String time =  date + "-" + String.valueOf(hour) + ":" + String.valueOf(minutes);

        SQLiteDatabase sqLiteDatabase = mHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbStructure.EventEntry.TITLE,title);
        contentValues.put(DbStructure.EventEntry.EVENT,event);
        contentValues.put(DbStructure.EventEntry.DATE,time);

        sqLiteDatabase.insertWithOnConflict(DbStructure.EventEntry.EVENT_TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        sqLiteDatabase.close();
        //no eto ne tocino

        Toast toast = Toast.makeText(getApplicationContext(), "Event submitted!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        finish();


    }

    @Override
    protected void onStop() {
        if(scheduleClient != null)
            scheduleClient.doUnbindService();
        super.onStop();
    }
}
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

public class UpdateActivity extends AppCompatActivity {
    TextView header;
    EditText new_titleEdit, new_eventEdit;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        Intent incoming = getIntent();
        String date = incoming.getStringExtra(ListActivity.DATE);
        String headerText = "Update event from " + date;
        header = (TextView) findViewById(R.id.textView3_1);
        header.setText(headerText);

        String eventInitial = incoming.getStringExtra(ListActivity.TITLE);
        String descriptionInitial = incoming.getStringExtra(ListActivity.DESCRIPTION);


        EditText title  = findViewById(R.id.new_title);
        EditText description = findViewById(R.id.new_description);

        title.setText(eventInitial);
        description.setText(descriptionInitial);



    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void SaveEvent(View view) {
        View parent = (View) view.getParent();
        TextView eventTitle = (TextView) parent.findViewById(R.id.event_title);

        Intent incoming = getIntent();
        String eventInitial = incoming.getStringExtra(ListActivity.TITLE);
        String descriptionInitial = incoming.getStringExtra(ListActivity.DESCRIPTION);

        EditText titleEdit = findViewById(R.id.new_title);
        EditText eventEdit = findViewById(R.id.new_description);
        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);


        EventsDbHelper mHelper = new EventsDbHelper(this);
        String event = String.valueOf(eventEdit.getText());
        String title = String.valueOf(titleEdit.getText());
        /* time is in the format yyyy/MM/dd-HH:mm */
         //String time =  date + "-" + String.valueOf(hour) + ":" + String.valueOf(minutes);

        sqLiteDatabase = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbStructure.EventEntry.TITLE, title);
        values.put(DbStructure.EventEntry.EVENT, event);
        //values.put(DbStructure.EventEntry.DATE, time);
        sqLiteDatabase.updateWithOnConflict(DbStructure.EventEntry.EVENT_TABLE_NAME, values, DbStructure.EventEntry.TITLE + " = ?", new String[]{eventInitial}, SQLiteDatabase.CONFLICT_REPLACE);
        sqLiteDatabase.close();

        Toast toast = Toast.makeText(getApplicationContext(), "Event updated!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
        finish();

        ListActivity listActivity = ListActivity.getInstance();
        listActivity.updateUI();
    }

    public void backButton(View view) {

        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
}
package com.example.mydailyplannerapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private static final String TAG = "ListActivity";

    private EventsDbHelper mHelper;
    private ListView listView;
    private EventAdapter mAdapter;
    public static ListActivity instance;
    public static  final String TITLE = "title";
    public static  final String DESCRIPTION = "description";
    public static  final String DATE = "date";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        instance = this;
        listView = (ListView) findViewById(R.id.eventsList);
        mHelper = new EventsDbHelper(this);
        updateUI();


    }

    public  static ListActivity getInstance()
    {
        return instance;
    }

    public void deleteEvent(View view) {
        View parent = (View) view.getParent();
        TextView title = (TextView)parent.findViewById(R.id.event_title);
        String event = String.valueOf(title.getText());
        SQLiteDatabase sqLiteDatabase = mHelper.getWritableDatabase();
       sqLiteDatabase.delete(DbStructure.EventEntry.EVENT_TABLE_NAME,
               TITLE + " = ?",
                new String[]{event});

       sqLiteDatabase.close();
        updateUI();
   }

    public void updateEvent(View view)
    {
        View parent = (View) view.getParent();
        TextView eventTitle = (TextView)parent.findViewById(R.id.event_title);
        String event = eventTitle.getText().toString();

        TextView eventContent = (TextView)parent.findViewById(R.id.event_text);
        String content = eventContent.getText().toString();

        TextView time = (TextView)parent.findViewById(R.id.event_time);
        String date = time.getText().toString();


        Intent intent = new Intent(this, UpdateActivity.class);
        intent.putExtra(TITLE, event);
        intent.putExtra(DESCRIPTION, content);

        intent.putExtra(DATE, date);
        startActivity(intent);
    }


    public void updateUI() {
        ArrayList<EventModel> eventList = new ArrayList<EventModel>();
        SQLiteDatabase sqLiteDatabase = mHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(DbStructure.EventEntry.EVENT_TABLE_NAME, new String[]{
                        DbStructure.EventEntry._ID,
                        TITLE,
                        DbStructure.EventEntry.EVENT,
                        DbStructure.EventEntry.DATE},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TITLE);
            String currentTitle = cursor.getString(cursor.getColumnIndex(TITLE));
            String currentContent = cursor.getString(cursor.getColumnIndex(DbStructure.EventEntry.EVENT));
            String currentDate = cursor.getString(cursor.getColumnIndex(DbStructure.EventEntry.DATE));
            eventList.add(new EventModel(currentTitle, currentContent, currentDate));
        }

        if (mAdapter == null) {
            mAdapter = new EventAdapter(this, eventList);
            listView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(eventList);
            mAdapter.notifyDataSetChanged();
        }

        cursor.close();
        sqLiteDatabase.close();
    }


}
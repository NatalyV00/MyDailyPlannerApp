package com.example.mydailyplannerapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class EventsDbHelper extends SQLiteOpenHelper {
    private final static String CREATE_EVENTS_TABLE = "CREATE TABLE "+ DbStructure.EventEntry.EVENT_TABLE_NAME + "( "
            + DbStructure.EventEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DbStructure.EventEntry.TITLE + " TEXT, "
            + DbStructure.EventEntry.EVENT + " TEXT, "
            + DbStructure.EventEntry.DATE + " TEXT)";
    private static final String DROP_EVENTS_TABLE = "DROP TABLE IF EXISTS " + DbStructure.EventEntry.EVENT_TABLE_NAME;

    public EventsDbHelper(@Nullable Context context)
    {
        super(context, DbStructure.DB_NAME, null, DbStructure.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_EVENTS_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    sqLiteDatabase.execSQL(DROP_EVENTS_TABLE);
    onCreate(sqLiteDatabase);
    }

    //a sa salvam evenemetul in addActivity
//    public void  SaveEvent (String title, String event, String date, SQLiteDatabase database)
//    {
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DbStructure.TITLE,title);
//        contentValues.put(DbStructure.EVENT,event);
//
//    }

}

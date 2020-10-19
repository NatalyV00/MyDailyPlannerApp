package com.example.mydailyplannerapp;


import android.provider.BaseColumns;

public class DbStructure {
    public static final String DB_NAME = "EVENTS_DB";
    public static final int DB_VERSION = 1;
    public static class EventEntry implements BaseColumns {
        public static final String EVENT_TABLE_NAME = "eventstable";
        public static final String TITLE = "title";
        public static final String EVENT = "event";
        public static final String DATE = "date";
    }

}

package com.example.mydailyplannerapp;

public class EventModel {
    public String title;
    public String content;
    public String date;

    public EventModel(String title, String content, String date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

}

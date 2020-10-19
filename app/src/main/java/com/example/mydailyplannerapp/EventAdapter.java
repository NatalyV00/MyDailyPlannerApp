package com.example.mydailyplannerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class EventAdapter extends ArrayAdapter<EventModel> {

    private Context context;
    private ArrayList<EventModel> eventList = new ArrayList<>();


    public EventAdapter(Context myContext, ArrayList<EventModel> myList) {
        super(myContext, 0, myList);
        context = myContext;
        eventList = myList;


    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        if (item == null) {
            item = LayoutInflater.from(context).inflate(R.layout.activity_event_adapter, parent, false);
        }

        EventModel currentEvent = eventList.get(position);
        TextView title = (TextView) item.findViewById(R.id.event_title);
        title.setText(currentEvent.getTitle());
        TextView content = (TextView) item.findViewById(R.id.event_text);
        content.setText(currentEvent.getContent());
        TextView time = (TextView) item.findViewById(R.id.event_time);
        time.setText(currentEvent.getDate());

        return item;
    }

}
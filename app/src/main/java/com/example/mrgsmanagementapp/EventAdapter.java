package com.example.mrgsmanagementapp;

//These are the imports for EventAdapter.java
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class EventAdapter extends ArrayAdapter<Event>
{

//  Constructor for EventAdapter
    public EventAdapter(@NonNull Context context, List<Event> events) {
        super(context, 0, events);
    }

    @NonNull
    @Override
//  This part of the code is to get Event item's position
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Event event = getItem(position);

//      if convertView is null, it opens the event_cell layout
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_cell, parent, false);
        TextView eventCellTV = convertView.findViewById(R.id.eventCellTV);
        String eventTitle = event.getName();
        eventCellTV.setText(eventTitle);
        return convertView;
    }
}

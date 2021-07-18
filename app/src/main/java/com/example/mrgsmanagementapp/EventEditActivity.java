package com.example.mrgsmanagementapp;

//These are imports for EventEditActivity
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class EventEditActivity extends AppCompatActivity
{
//  These are for creating variables
    private EditText eventNameET;
    private TextView eventDateTV;
    Button timeButton;
    int hour, minute;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        timeButton = findViewById(R.id.eventTimeTV);
//      for showing the user the selected date they chose to add event in
        eventDateTV.setText("Date: "+ CalendarUtils.formattedDate(CalendarUtils.selectedDate));
    }

//  initWidgets method
    private void initWidgets()
    {
//      Assigning Variables with id's defined in activity_event_edit.xml
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
    }

//  when save button is clicked
    public void saveEventAction(View view)
    {
        String eventName = eventNameET.getText().toString();
        Event newEvent = new Event(eventName, CalendarUtils.selectedDate); // comment this and uncomment the bottom one
        Event.eventsList.add(newEvent);
        finish();
    }

//  This is for the pop up time picker in event
    public void popTimePicker(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = (view1, selectedHour, selectedMinute) -> {
            hour = selectedHour;
            minute = selectedMinute;
//          Format for the time (2 digits)
            timeButton.setText(String.format(Locale.getDefault(),"%02d:%02d",hour ,minute));

        };
//      I chose to have a scrollable time picker instead of the clock! This part of the code is for the scrollable time picker
        int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);
//      Title for timepicker
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();

    }
}
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
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class EventEditActivity extends AppCompatActivity
{
    //  These are for creating variables
    private EditText eventNameET;
    private TextView eventDateTV;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
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
        if (eventName.isEmpty()){
            showError(eventNameET,"Please enter an Event");
        } else {
            Event.eventsList.add(newEvent);
            Toast.makeText(EventEditActivity.this, "New Event Added", Toast.LENGTH_SHORT).show();
            finish();
        }


    }

    private void showError(EditText field, String text) {
        field.setError(text);
        field.requestFocus();
    }

}
package com.example.mrgsmanagementapp;

//These are the imports for WeekViewActivity.java
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.example.mrgsmanagementapp.CalendarUtils.daysInWeekArray;
import static com.example.mrgsmanagementapp.CalendarUtils.monthYearFromDate;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WeekViewActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    //  These are for creating variables
    private ListView eventListView;
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        initWidgets();
        setWeekView();

    }

    //  Creating initWidgets method
    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
    }

    //  Creating setWeekView method
    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

//      This part of the code is for the amount of cells (grids) in week view - I chose 7 because of 7 days in week
        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);

    }

    //  This part of the code is for viewing Previous Week from CalendarUtils
    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    //  This part of the code is for viewing Next Week from CalendarUtils
    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    //  onItemClick is the selected date in week view
    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }

    //  Creation of onResume method
    @Override
    protected void onResume() {
        super.onResume();
        setEventAdapter();
    }

    //  Creation of setEventAdapter method
    private void setEventAdapter()
    {
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
    }

    //  Redirects to EventEditActivity
    public void newEventAction(View view)
    {
        startActivity(new Intent(this, EventEditActivity.class));
    }
}
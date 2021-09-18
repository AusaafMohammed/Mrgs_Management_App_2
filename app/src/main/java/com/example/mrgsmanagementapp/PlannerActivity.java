package com.example.mrgsmanagementapp;

//These are the imports for PlannerActivity.java
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
//import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static com.example.mrgsmanagementapp.CalendarUtils.daysInMonthArray;
import static com.example.mrgsmanagementapp.CalendarUtils.monthYearFromDate;

public class PlannerActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{

//  These are for creating variables
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);
        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();

    }

//  Creating initWidgets method
    private void initWidgets() {
//      defining the variables
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }
//  Creating setMonthView method
    private void setMonthView()
    {
//      This part of the code is for the design of calendar with 7 grids and selected date
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

//  This part of the code is for viewing Previous Month from CalendarUtils
    public void previousMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

//  This part of the code is for viewing Next Month from CalendarUtils
    public void nextMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
//      If date is selected
        if (date != null)
        {
            CalendarUtils.selectedDate = date;
            setMonthView();
        }
    }

    public void event_btn (View view) {
        startActivity(new Intent(this, EventActivity.class));
    }
}
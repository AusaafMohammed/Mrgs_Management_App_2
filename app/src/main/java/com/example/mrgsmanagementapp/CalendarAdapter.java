package com.example.mrgsmanagementapp;

//These are the imports for CalendarAdapter.java
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    //  These are for creating variables
    private final ArrayList<LocalDate> days;
    private final OnItemListener onItemListener;

    //  Creation of constructor CalendarAdapter
    public CalendarAdapter(ArrayList<LocalDate> days, OnItemListener onItemListener)
    {
        this.days = days;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @NotNull
    @Override
//  Implementation of the OnCreateViewHolder
    public CalendarViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
//      This part reads the calendar_cell.xml file
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams=view.getLayoutParams();
//      This is for the size of each cell
        if (days.size() > 15 ) //cell size for month view
            layoutParams.height = (int) (parent.getHeight() * 0.15);
        else //cell size for week view
            layoutParams.height = (int) parent.getHeight();
        return new CalendarViewHolder(view, onItemListener, days);
    }

    //  Implementation of the OnBindViewHolder
    @Override
    public void onBindViewHolder(@NonNull @NotNull CalendarViewHolder holder, int position)
    {
//      This is if the cells are empty, they appear empty and don't contain any value in them... This also avoids the user from selecting empty cells
        final LocalDate date = days.get(position);
        if (date == null)
            holder.dayOfMonth.setText("");
        else {
//          if holder is not empty, then the user can select the date and the date background appears white
            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
            if (date.equals(CalendarUtils.selectedDate))
                holder.parentView.setBackgroundColor(Color.WHITE);
        }
    }

    //  Implementation of the getItemCount
    @Override
    public int getItemCount()
    {
        return days.size();
    }

    //  Implementation of the OnItemListener
    public interface OnItemListener{
        void onItemClick(int position, LocalDate date);
    }
}


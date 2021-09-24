package com.example.mrgsmanagementapp;

//These are the imports for Event.java
import java.time.LocalDate;
import java.util.ArrayList;

public class Event
{

    //   Creating an ArrayList for events
    public static ArrayList<Event> eventsList = new ArrayList<>();

    //  This part receives the date
    public static ArrayList<Event> eventsForDate(LocalDate date)
    {
        ArrayList<Event> events = new ArrayList<>();

        for(Event event : eventsList)
        {
            if(event.getDate().equals(date))
                events.add(event);
        }

        return events;
    }

    //  Setting variables
    private String name;
    private LocalDate date;

    //  Generating constructors for each of the variables
    public Event(String name, LocalDate date) {
        this.name = name;
        this.date = date;
    }

    //  Getters and Setters for name,date and time
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
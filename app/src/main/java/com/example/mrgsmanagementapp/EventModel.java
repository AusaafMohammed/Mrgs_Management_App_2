package com.example.mrgsmanagementapp;

public class EventModel {

    private String event, description, id, date;

    public EventModel() {

    }

    public EventModel(String event, String description, String id, String date) {
        this.event = event;
        this.description = description;
        this.id = id;
        this.date = date;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

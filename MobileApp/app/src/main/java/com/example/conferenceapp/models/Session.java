package com.example.conferenceapp.models;

public class Session {

    private int ID;
    private String title;
    private CustomTime dateTime;
    private String type;
    private boolean clickable;

    public Session(int ID, String title, CustomTime dateTime, String type, boolean clickable) {
        this.ID = ID;
        this.title = title;
        this.dateTime = dateTime;
        this.type = type;
        this.clickable = clickable;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CustomTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(CustomTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }
}

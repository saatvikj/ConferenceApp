package com.example.conferenceapp.models;

public class Food {

    public String time;
    public String location;
    public String type;
    public String description;

    public Food(String time, String location, String type, String description) {
        this.time = time;
        this.location = location;
        this.type = type;
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public int getStartTime () {
        String start_time = time.split("-")[0];
        String hour_format = start_time.replace(":","");
        return Integer.parseInt(hour_format);
    }

    public int getEndTime() {
        String start_time = time.split("-")[1];
        String hour_format = start_time.replace(":","");
        return Integer.parseInt(hour_format);
    }
}
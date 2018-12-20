package com.example.conferenceapp.models;

public class Food {

    public String time;
    public String type;


    public Food (String time, String type) {
        this.time = time;
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public String getType() {
        return type;
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
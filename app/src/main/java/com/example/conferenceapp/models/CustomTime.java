package com.example.conferenceapp.models;

import java.io.Serializable;
import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;

public class CustomTime implements Serializable {

    public String date;
    public String startTime;
    public String endTime;
    public String day;

    public CustomTime(String date, String startTime, String endTime, String day) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDay() {
        return day;
    }

    public String displayTime() {
        return startTime.concat("-").concat(endTime);
    }

    @Override
    public String toString() {
        return day.concat(",").concat(date).concat(",").concat(startTime).concat("-").concat(endTime);
    }

    public ArrayList<Time> getParseTime(String time){
        String startTime = time.split("-")[0];
        String endTime = time.split("-")[1];
        if(startTime.charAt(startTime.length()-2) == 'a'){

        }
    }
}
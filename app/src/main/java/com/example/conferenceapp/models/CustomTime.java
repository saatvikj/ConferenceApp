package com.example.conferenceapp.models;

import java.io.Serializable;
import java.sql.Time;
import java.time.DayOfWeek;
import java.util.Date;

public class CustomTime implements Serializable{

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

    public String displayTime(){
        return startTime.concat("-").concat(endTime);
    }
}
package com.example.conferenceapp;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.Date;

public class CustomTime {

    public Date date;
    public java.sql.Time startTime;
    public java.sql.Time endTime;
    public DayOfWeek day;

    public CustomTime(Date date, Time startTime, Time endTime, DayOfWeek day) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
    }

    public Date getDate() {
        return date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public DayOfWeek getDay() {
        return day;
    }
}
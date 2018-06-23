package com.example.conferenceapp.models;

import java.io.Serializable;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomTime implements Serializable {
    public String months[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
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

    public int getStartTimeHour() {
        return Integer.parseInt(this.startTime.split(":")[0]);
    }

    public int getEndTimeHour() {
        return Integer.parseInt(this.endTime.split(":")[0]);
    }


    public Long getParseStartTime() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
        String date_components[] = date.split(" ");
        String year = date_components[2];
        int index = 0;
        for (int i = 0; i < months.length; i++) {
            if (months[i].equalsIgnoreCase(date_components[1])) {
                index = i + 1;
            }
        }
        String month = index >= 10 ? Integer.toString(index) : "0" + Integer.toString(index);
        int day = Integer.parseInt(date_components[0]);
        String date = day >= 10 ? Integer.toString(day) : "0" + Integer.toString(day);
        String time = startTime.replace(":", "");
        try {
            Date dt = sdf.parse(year.concat(month).concat(date).concat(time));
            Long millis = dt.getTime();
            return millis;

        } catch (Exception e) {
        }
        return (long) 0;

    }

    public Long getParseEndTime() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
        String date_components[] = date.split(" ");
        String year = date_components[2];
        int index = 0;
        for (int i = 0; i < months.length; i++) {
            if (months[i].equalsIgnoreCase(date_components[1])) {
                index = i + 1;
            }
        }
        String month = index >= 10 ? Integer.toString(index) : "0" + Integer.toString(index);
        int day = Integer.parseInt(date_components[0]);
        String date = day >= 10 ? Integer.toString(day) : "0" + Integer.toString(day);
        String time = endTime.replace(":", "");
        try {
            Date dt = sdf.parse(year.concat(month).concat(date).concat(time));
            Long millis = dt.getTime();
            return millis;

        } catch (Exception e) {
        }
        return (long) 0;

    }
}
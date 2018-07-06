package com.example.conferenceapp.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomTime implements Serializable {
    public String months[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    public String date;
    public String startTime;
    public String endTime;
    public String day;

    public CustomTime(String date, String startTime, String endTime) {
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
        return date.concat(",").concat(startTime).concat("-").concat(endTime);
    }

    public int getStartTimeHour() {
        return Integer.parseInt(this.startTime.split(":")[0]);
    }

    public int getEndTimeHour() {
        return Integer.parseInt(this.endTime.split(":")[0]);
    }

    public int getStartTimeMinute() {
        return Integer.parseInt(this.startTime.split(":")[1]);
    }

    public int getEndTimeMinute() {
        return Integer.parseInt(this.endTime.split(":")[1]);
    }


    public Long getParseStartTime() {

        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmm");

        String time = startTime.replace(":", "");
        try {
            Date dt = sdf.parse(date.replace("/","").concat(time));
            Long millis = dt.getTime();
            return millis;

        } catch (Exception e) {
        }
        return (long) 0;

    }

    public Long getParseEndTime() {

        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmm");

        String time = endTime.replace(":", "");
        try {
            Date dt = sdf.parse(date.replace("/","").concat(time));
            Long millis = dt.getTime();
            return millis;

        } catch (Exception e) {
        }
        return (long) 0;

    }
}
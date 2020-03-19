package com.example.conferenceapp.models;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

public class Conference {

    private String conference_id;
    private String conference_name;
    private String conference_venue;
    private About conference_about;
    private String conference_start_day;
    private String conference_end_day;
    private Partner[] conference_partners;
    private Food[] conference_food_guide;

    public Conference(String conference_id, String conference_name, String conference_venue, About conference_about, String conference_start_day, String conference_end_day, Partner[] conference_partners, Food[] food_guide) {
        this.conference_id = conference_id;
        this.conference_name = conference_name;
        this.conference_venue = conference_venue;
        this.conference_about = conference_about;
        this.conference_start_day = conference_start_day;
        this.conference_end_day = conference_end_day;
        this.conference_partners = conference_partners;
        this.conference_food_guide = food_guide;
    }

    public String getConference_id() { return conference_id; }

    public String getConference_name() {
        return conference_name;
    }

    public String getConference_venue() {
        return conference_venue;
    }

    public About getConference_about() {
        return conference_about;
    }

    public String getConference_start_day() {
        return conference_start_day;
    }

    public String getConference_end_day() {
        return conference_end_day;
    }

    public Partner[] getConference_partners() {
        return conference_partners;
    }

    public Food[] getConference_food_guide() {
        return conference_food_guide;
    }

    public String getConferenceDates() throws ParseException {

        SimpleDateFormat simpleDateFormat_1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat_2 = new SimpleDateFormat("dd MMM");
        SimpleDateFormat simpleDateFormat_3 = new SimpleDateFormat("YY");
        Date start_date = simpleDateFormat_1.parse(conference_start_day);
        Date end_date = simpleDateFormat_1.parse(conference_end_day);

        String dates = simpleDateFormat_2.format(start_date).concat(" - ").concat(simpleDateFormat_2.format(end_date)).concat("'").concat(simpleDateFormat_3.format(start_date));

        return dates;
    }

    public Food[] get_guide_for_day(String date) {

        ArrayList<Food> days_guide = new ArrayList<>();

        for (int i = 0; i < conference_food_guide.length; i++) {
            if (conference_food_guide[i].time.date.equalsIgnoreCase(date)) {
                days_guide.add(conference_food_guide[i]);
            }
        }

        return days_guide.toArray(new Food[days_guide.size()]);
    }
}

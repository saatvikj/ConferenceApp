package com.example.conferenceapp.models;

public class Conference {

    private String conference_name;
    private String conference_venue;
    private About conference_about;
    private String conference_start_day;
    private String conference_end_day;
    private Partner[] conference_partners;
    private Food[] conference_food_guide;

    public Conference(String conference_name, String conference_venue, About conference_about, String conference_start_day, String conference_end_day, Partner[] conference_partners, Food[] food_guide) {
        this.conference_name = conference_name;
        this.conference_venue = conference_venue;
        this.conference_about = conference_about;
        this.conference_start_day = conference_start_day;
        this.conference_end_day = conference_end_day;
        this.conference_partners = conference_partners;
        this.conference_food_guide = food_guide;
    }

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
}

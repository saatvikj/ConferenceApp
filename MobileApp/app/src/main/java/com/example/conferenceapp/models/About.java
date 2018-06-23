package com.example.conferenceapp.models;

public class About {
    private String description;
    private String website;
    private String facebook;
    private String twitter;
    private String contact;

    public About(String description, String website, String facebook, String twitter, String contact) {
        this.description = description;
        this.website = website;
        this.facebook = facebook;
        this.twitter = twitter;
        this.contact = contact;
    }

    public String getWebsite() {
        return website;
    }

    public String getDescription() {
        return description;
    }

    public String getFacebook() {
        return facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getContact() {
        return contact;
    }
}

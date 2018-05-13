package com.example.conferenceapp;

public class Partner {
    private int id;
    private String sponsorName;
    private String sponsorType;
    private String sponsorWebsite;
    private int imageID;

    public Partner(int id, String sponsorName, String sponsorType, String website, int imageID) {
        this.id = id;
        this.sponsorName = sponsorName;
        this.sponsorType = sponsorType;
        this.sponsorWebsite = website;
        this.imageID = imageID;
    }

    public int getId() {
        return id;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public String getSponsorType() {
        return sponsorType;
    }

    public int getImageID() {
        return imageID;
    }

    public String getSponsorWebsite() {
        return sponsorWebsite;
    }
}

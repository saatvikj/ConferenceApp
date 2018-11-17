package com.example.conferenceapp.models;

public class Profile {

    private String emailID;
    private String bio;
    private String researchInterests;
    private String website;
    private String twitter;
    private String linkedin;


    public Profile(){

    }

    public Profile(String emailID, String bio, String researchInterests, String website, String twitter, String linkedin) {
        this.emailID = emailID;
        this.bio = bio;
        this.researchInterests = researchInterests;
        this.website = website;
        this.twitter = twitter;
        this.linkedin = linkedin;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getResearchInterests() {
        return researchInterests;
    }

    public void setResearchInterests(String researchInterests) {
        this.researchInterests = researchInterests;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }
}

package com.example.conferenceapp.models;

public class Paper {

    private String title;
    private String venue;
    private CustomTime time;
    private User authors[];
    private String paper_abstract;
    private String topics[];

    public String getTitle() {
        return title;
    }

    public String getVenue() {
        return venue;
    }

    public CustomTime getTime() {
        return time;
    }

    public User[] getAuthors() {
        return authors;
    }

    public String[] getTopics() {
        return topics;
    }

    public String getPaper_abstract() {
        return paper_abstract;
    }

    public Paper(String title, String venue, CustomTime time, User[] authors, String[] topics, String paper_abstract) {
        this.title = title;
        this.venue = venue;
        this.time = time;
        this.authors = authors;
        this.topics = topics;
        this.paper_abstract = paper_abstract;
    }
}

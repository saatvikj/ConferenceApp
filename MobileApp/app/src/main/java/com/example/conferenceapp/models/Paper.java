package com.example.conferenceapp.models;

import java.io.Serializable;

public class Paper implements Serializable {

    private String title;
    private String venue;
    private CustomTime time;
    private String authors[];
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

    public String[] getAuthors() {
        return authors;
    }

    public String[] getTopics() {
        return topics;
    }

    public String getPaper_abstract() {
        return paper_abstract;
    }

    public Paper(String title, String venue, CustomTime time, String[] authors, String[] topics, String paper_abstract) {
        this.title = title;
        this.venue = venue;
        this.time = time;
        this.authors = authors;
        this.topics = topics;
        this.paper_abstract = paper_abstract;
    }
}

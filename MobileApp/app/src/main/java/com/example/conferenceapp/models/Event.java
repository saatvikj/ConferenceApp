package com.example.conferenceapp.models;

public class Event {

    private String title;
    private String _abstract;
    private String[] authors;
    private String room;

    public Event(){

    }

    public Event(String title, String _abstract, String[] authors, String room) {
        this.title = title;
        this._abstract = _abstract;
        this.authors = authors;
        this.room = room;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String get_abstract() {
        return _abstract;
    }

    public void set_abstract(String _abstract) {
        this._abstract = _abstract;
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}

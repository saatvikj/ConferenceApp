package com.example.conferenceapp.models;

import java.util.ArrayList;

public class User {

    private String ID;
    private String name;
    private String email;
    private String company;
    private String joining_code;

    public User() {
    }

    public User(String name, String email, String company) {
        this.name = name;
        this.email = email;
        this.company = company;
    }

    public User(String ID, String name, String email, String company, String joining_code) {
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.company = company;
        this.joining_code = joining_code;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJoining_code() {
        return joining_code;
    }

    public void setJoining_code(String joining_code) {
        this.joining_code = joining_code;
    }

}

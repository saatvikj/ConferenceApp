package com.example.conferenceapp.models;

public class User {

    private String ID;
    private String name;
    private String email;
    private String company;
    private String location;
    private String bio;
    private String interests[];
    private Paper presentedPapers[];
    private String typeOfUser;
    private Paper myAgenda[];

    public User(String name, String company) {
        this.name = name;
        this.company = company;
    }

    public User(String name, String email, String company, String location) {
        this.name = name;
        this.email = email;
        this.company = company;
        this.location = location;
    }

    public User(String ID, String name, String email, String company, String location, String bio, String[] interests, Paper[] presentedPapers, String typeOfUser, Paper[] myAgenda) {
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.company = company;
        this.location = location;
        this.bio = bio;
        this.interests = interests;
        this.presentedPapers = presentedPapers;
        this.typeOfUser = typeOfUser;
        this.myAgenda = myAgenda;
    }

    public User(String ID) {
        this.ID = ID;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String[] getInterests() {
        return interests;
    }

    public void setInterests(String[] interests) {
        this.interests = interests;
    }

    public Paper[] getPresentedPapers() {
        return presentedPapers;
    }

    public void setPresentedPapers(Paper[] presentedPapers) {
        this.presentedPapers = presentedPapers;
    }

    public String getTypeOfUser() {
        return typeOfUser;
    }

    public void setTypeOfUser(String typeOfUser) {
        this.typeOfUser = typeOfUser;
    }

    public Paper[] getMyAgenda() {
        return myAgenda;
    }

    public void setMyAgenda(Paper[] myAgenda) {
        this.myAgenda = myAgenda;
    }
}

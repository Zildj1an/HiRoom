package com.cgaxtr.hiroom.model;

public class User {

    private int id;
    private String name;
    private String email;
    private String pass;
    private String pathImg;
    private String city;
    private String gender;
    private Boolean smoker;
    private String worker;
    private String description;
    private int partying;
    private int organized;
    private int athlete;
    private int freak;
    private int sociable;
    private int active;

    public User(String name, String email, String imgPath){
        this.name = name;
        this.email = email;
        this.pathImg = imgPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPathImg() {
        return pathImg;
    }

    public void setPathImg(String pathImg) {
        this.pathImg = pathImg;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getSmoker() {
        return smoker;
    }

    public void setSmoker(Boolean smoker) {
        this.smoker = smoker;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPartying() {
        return partying;
    }

    public void setPartying(int partying) {
        this.partying = partying;
    }

    public int getOrganized() {
        return organized;
    }

    public void setOrganized(int organized) {
        this.organized = organized;
    }

    public int getAthlete() {
        return athlete;
    }

    public void setAthlete(int athlete) {
        this.athlete = athlete;
    }

    public int getFreak() {
        return freak;
    }

    public void setFreak(int freak) {
        this.freak = freak;
    }

    public int getSociable() {
        return sociable;
    }

    public void setSociable(int sociable) {
        this.sociable = sociable;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}

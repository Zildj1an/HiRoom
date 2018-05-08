package com.cgaxtr.hiroom.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable{

    private int id;
    private String name;
    private String surname;
    private String email;
    private int phoneNumber;
    private String birthDate;
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

    public User(){ }

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(surname);
        dest.writeString(email);
        dest.writeInt(phoneNumber);
        dest.writeString(birthDate);
        dest.writeString(pass);
        dest.writeString(pathImg);
        dest.writeString(city);
        dest.writeString(gender);
        dest.writeByte((byte) (smoker ? 1 : 0));
        dest.writeString(worker);
        dest.writeString(description);
        dest.writeInt(partying);
        dest.writeInt(organized);
        dest.writeInt(athlete);
        dest.writeInt(freak);
        dest.writeInt(sociable);
        dest.writeInt(active);
    }

    protected User(Parcel in) {
        id = in.readInt();
        name = in.readString();
        surname = in.readString();
        email = in.readString();
        phoneNumber = in.readInt();
        birthDate = in.readString();
        pass = in.readString();
        pathImg = in.readString();
        city = in.readString();
        gender = in.readString();
        smoker = in.readByte() != 0;
        worker = in.readString();
        description = in.readString();
        partying = in.readInt();
        organized = in.readInt();
        athlete = in.readInt();
        freak = in.readInt();
        sociable = in.readInt();
        active = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}

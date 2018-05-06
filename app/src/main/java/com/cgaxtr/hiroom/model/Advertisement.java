package com.cgaxtr.hiroom.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Advertisement implements Parcelable{

    private int idAdvertisement;
    private String address;
    private int price;
    private int size;
    private int floor;
    private String description;
    private List<String> images;
    private int ownerId;
    private String ownerName;
    private String ownerEmail;
    private int ownerPhone;


    public int getIdAdvertisement() {
        return idAdvertisement;
    }

    public void setIdAdvertisement(int idAdvertisement) {
        this.idAdvertisement = idAdvertisement;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public int getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(int ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(idAdvertisement);
        dest.writeString(address);
        dest.writeInt(price);
        dest.writeInt(size);
        dest.writeInt(floor);
        dest.writeString(description);
        dest.writeStringList(images);
        dest.writeInt(ownerId);
        dest.writeString(ownerName);
        dest.writeString(ownerEmail);
        dest.writeInt(ownerPhone);
    }

    protected Advertisement(Parcel in) {
        idAdvertisement = in.readInt();
        address = in.readString();
        price = in.readInt();
        size = in.readInt();
        floor = in.readInt();
        description = in.readString();
        images = in.createStringArrayList();
        ownerId = in.readInt();
        ownerName = in.readString();
        ownerEmail = in.readString();
        ownerPhone = in.readInt();
    }

    public static final Creator<Advertisement> CREATOR = new Creator<Advertisement>() {
        @Override
        public Advertisement createFromParcel(Parcel in) {
            return new Advertisement(in);
        }

        @Override
        public Advertisement[] newArray(int size) {
            return new Advertisement[size];
        }
    };
}

package com.example.bassant.finderapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hp on 6/27/2017.
 */

public class Request {
    private String userName;
    private String date;
    private String firstName;
    private Character gender;
    List<String> Images = new ArrayList<String>();


    public Request() {}

    public Request(String userName, String date, String firstName, Character gender, List<String> images) {
        this.userName = userName;
        this.date = date;
        this.firstName = firstName;
        this.gender = gender;
        Images = images;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public List<String> getImages() {
        return Images;
    }

    public void setImages(List<String> images) {
        Images = images;
    }

    public void addImage(String image) {
        Images.add(image);
    }
}

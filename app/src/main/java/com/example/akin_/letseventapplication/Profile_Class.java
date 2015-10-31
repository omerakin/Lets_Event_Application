package com.example.akin_.letseventapplication;

import android.graphics.Bitmap;

/**
 * Created by akin_ on 31.10.2015.
 */
public class Profile_Class {

    private String profile_id;
    private Bitmap profile_picture;
    private String name;
    private String surname;
    private String email;
    private String telephone;

    public Profile_Class (String profile_id, Bitmap profile_picture, String name, String surname, String email, String telephone ) {

        this.profile_id = profile_id;
        this.profile_picture = profile_picture;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.telephone = telephone;

    }


    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }

    public Bitmap getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(Bitmap profile_picture) {
        this.profile_picture = profile_picture;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}

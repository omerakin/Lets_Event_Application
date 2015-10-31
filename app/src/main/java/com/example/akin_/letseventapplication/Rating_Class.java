package com.example.akin_.letseventapplication;

/**
 * Created by akin_ on 31.10.2015.
 */
public class Rating_Class {

    private String event_id;
    private String profile_id;
    private double rating;

    public Rating_Class (String event_id, String profile_id, double rating) {

        this.event_id = event_id;
        this.profile_id = profile_id;
        this.rating = rating;

    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}

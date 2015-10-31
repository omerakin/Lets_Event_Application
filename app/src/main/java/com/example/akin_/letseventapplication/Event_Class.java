package com.example.akin_.letseventapplication;

import android.graphics.Bitmap;

/**
 * Created by akin_ on 31.10.2015.
 */
public class Event_Class {

    private String event_id;
    private String event_name;
    private Bitmap event_picture;
    private String event_date_created;
    private String event_date;
    private String event_creator;
    private String event_location;
    private String event_coordinates;
    private String event_price;
    private double event_rating_average;
    private String where_to_buy;

    public Event_Class (String event_id, String event_name, Bitmap event_picture, String event_date_created, String event_date,
                        String event_creator, String event_location, String event_coordinates, String event_price,
                        double event_rating_average, String where_to_buy) {

        this.event_id = event_id;
        this.event_name = event_name;
        this.event_picture = event_picture;
        this.event_date_created = event_date_created;
        this.event_date = event_date;
        this.event_creator = event_creator;
        this.event_location = event_location;
        this.event_coordinates = event_coordinates;
        this.event_price = event_price;
        this.event_rating_average = event_rating_average;
        this.where_to_buy = where_to_buy;
    }


    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public Bitmap getEvent_picture() {
        return event_picture;
    }

    public void setEvent_picture(Bitmap event_picture) {
        this.event_picture = event_picture;
    }

    public String getEvent_date_created() {
        return event_date_created;
    }

    public void setEvent_date_created(String event_date_created) {
        this.event_date_created = event_date_created;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getEvent_creator() {
        return event_creator;
    }

    public void setEvent_creator(String event_creator) {
        this.event_creator = event_creator;
    }

    public String getEvent_location() {
        return event_location;
    }

    public void setEvent_location(String event_location) {
        this.event_location = event_location;
    }

    public String getEvent_coordinates() {
        return event_coordinates;
    }

    public void setEvent_coordinates(String event_coordinates) {
        this.event_coordinates = event_coordinates;
    }

    public String getEvent_price() {
        return event_price;
    }

    public void setEvent_price(String event_price) {
        this.event_price = event_price;
    }

    public double getEvent_rating_average() {
        return event_rating_average;
    }

    public void setEvent_rating_average(double event_rating_average) {
        this.event_rating_average = event_rating_average;
    }

    public String getWhere_to_buy() {
        return where_to_buy;
    }

    public void setWhere_to_buy(String where_to_buy) {
        this.where_to_buy = where_to_buy;
    }
}

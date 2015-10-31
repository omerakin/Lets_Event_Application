package com.example.akin_.letseventapplication;

/**
 * Created by akin_ on 31.10.2015.
 */
public class Comment_Class {

    private String event_id;
    private String profile_id;
    private int comment_id;
    private double comment_point;
    private String comment;

    public  Comment_Class (String event_id, String profile_id, int comment_id, double comment_point, String comment) {

        this.event_id = event_id;
        this.profile_id = profile_id;
        this.comment_id = comment_id;
        this.comment_point = comment_point;
        this.comment =comment;

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

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public double getComment_point() {
        return comment_point;
    }

    public void setComment_point(double comment_point) {
        this.comment_point = comment_point;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

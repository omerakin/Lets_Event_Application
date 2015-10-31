package com.example.akin_.letseventapplication;

/**
 * Created by akin_ on 31.10.2015.
 */
public class Account_Class {

    private String username;
    private String password;
    private String GUID;
    private Boolean activation;

    public Account_Class (String username, String password, String GUID, Boolean activation) {

        this.username = username;
        this.password = password;
        this.GUID = GUID;
        this. activation = activation;

    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGUID() {
        return GUID;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    public Boolean getActivation() {
        return activation;
    }

    public void setActivation(Boolean activation) {
        this.activation = activation;
    }
}

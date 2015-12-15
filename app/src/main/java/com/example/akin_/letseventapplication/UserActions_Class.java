package com.example.akin_.letseventapplication;

/**
 * Created by Yusuf Erdal on 15.12.2015.
 */
public class UserActions_Class {
    private String typeOfAction;
    private String actionBy;
    private String actionTo;

    public String getTypeOfAction(){
        return typeOfAction;
    }
    public void setTypeOfAction(String typeOfAction){
        this.typeOfAction = typeOfAction;
    }
    public String getActionBy(){
        return actionBy;
    }
    public void setActionBy(String actionBy){
        this.actionBy = actionBy;
    }
    public String getActionTo(){
        return actionTo;
    }
    public void setActionTo(String actionTo){
        this.actionTo = actionTo;
    }
}

package com.example.akin_.letseventapplication;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private TextView NameLastname;
    private String UsernameString;
    private String PasswordString;
    private ImageButton imageButton;
    private String ObjectIdOfUser;
    private TextView EventsNumber;
    private TextView FriendNumber;
    private ArrayList<String> ObjectIdofFriendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ObjectIdofFriendList = new ArrayList<>();

        imageButton = (ImageButton) findViewById(R.id.profilePictureButton);
        EventsNumber = (TextView) findViewById(R.id.EventsNumber);
        FriendNumber = (TextView) findViewById(R.id.FriendNumber);

        TabHost tabHost3 = (TabHost) findViewById(R.id.tabHost3);
        LocalActivityManager mLocalActivityManager = new LocalActivityManager(this, false);
        mLocalActivityManager.dispatchCreate(savedInstanceState);
        tabHost3.setup(mLocalActivityManager);

        TabHost.TabSpec profile_MyEvents_Activity = tabHost3.newTabSpec("MyEvents");
        profile_MyEvents_Activity.setIndicator("My Events");
        profile_MyEvents_Activity.setContent(new Intent(this, Profile_MyEvents_Activity.class));

        TabHost.TabSpec Profile_FutureEvents_Activity = tabHost3.newTabSpec("FutureEvents");
        Profile_FutureEvents_Activity.setIndicator("Future Events");
        Profile_FutureEvents_Activity.setContent(new Intent(this, Profile_FutureEvents_Activity.class));

        TabHost.TabSpec profile_PastEvents_Activity = tabHost3.newTabSpec("PastEvents");
        profile_PastEvents_Activity.setIndicator("Past Events");
        profile_PastEvents_Activity.setContent(new Intent(this, Profile_PastEvents_Activity.class));

        tabHost3.addTab(profile_MyEvents_Activity);
        tabHost3.addTab(Profile_FutureEvents_Activity);
        tabHost3.addTab(profile_PastEvents_Activity);

        TextView myEventsTextView = (TextView) tabHost3.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
        myEventsTextView.setTextSize(10);
        TextView EventsToAttendTextView = (TextView) tabHost3.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
        EventsToAttendTextView.setTextSize(9);
        TextView pastEventsTextView = (TextView) tabHost3.getTabWidget().getChildAt(2).findViewById(android.R.id.title);
        pastEventsTextView.setTextSize(10);


        setProfilePicture();
        setNameAndLastnameOfUser();
        getNameandLastnameFromParse();
        getEventNumber();
        getFriendNumber();


    }

    private void setProfilePicture() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("TestAccount");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> parseAccounts, ParseException e) {
                if (e == null) {
                    int len = parseAccounts.size();
                    for (int i = 0; i < len; i++) {
                        //get the i th element of Accounts in order to obtain details
                        ParseObject p = parseAccounts.get(i);

                        //get the detail information of userAccount
                        String pEmail = p.getString("Email");
                        String pPassword = p.getString("Password");
                        String pSex;

                        if(pEmail.equals(UsernameString) && pPassword.equals(PasswordString)){
                            pSex = p.getString("Sex");
                            if (pSex.equals("Female")){
                                imageButton.setImageResource(R.mipmap.female);
                            } else if (pSex.equals("Male")) {
                                imageButton.setImageResource(R.mipmap.male);
                            } else if (pSex.equals("Facebook")) {
                                imageButton.setImageResource(R.mipmap.facebook);
                            }
                            return;
                        }
                    }
                } else {
                    System.out.println("Error::: in setProfilePicture()!");

                }
            }
        });


    }

    private void setNameAndLastnameOfUser() {

        // Read Username from file
        FileInputStream fisUsername = null;
        try {
            fisUsername = openFileInput("UsernameInfromation.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedInputStream bisUsername = new BufferedInputStream(fisUsername);
        StringBuffer Username = new StringBuffer();
        try {
            while (bisUsername.available() != 0){
                char next = (char) bisUsername.read();
                Username.append(next);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        UsernameString = Username.toString();

        // Read Password from file
        FileInputStream fisPassword = null;
        try {
            fisPassword = openFileInput("PasswordInfromation.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedInputStream bisPassword = new BufferedInputStream(fisPassword);
        StringBuffer Password = new StringBuffer();
        try {
            while (bisPassword.available() != 0){
                char next = (char) bisPassword.read();
                Password.append(next);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        PasswordString = Password.toString();

        // Read ObjectId from file
        FileInputStream fisObjectId = null;
        try {
            fisObjectId = openFileInput("ObjectidInfromation.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedInputStream bisObjectId = new BufferedInputStream(fisObjectId);
        StringBuffer ObjectId = new StringBuffer();
        try {
            while (bisObjectId.available() != 0){
                char next = (char) bisObjectId.read();
                ObjectId.append(next);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObjectIdOfUser = ObjectId.toString();
    }

    private void getNameandLastnameFromParse() {

        NameLastname = (TextView) findViewById(R.id.nameLastname);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("TestAccount");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> parseAccounts, ParseException e) {
                if (e == null) {
                    int len = parseAccounts.size();
                    for (int i = 0; i < len; i++) {
                        //get the i th element of Accounts in order to obtain details
                        ParseObject p = parseAccounts.get(i);

                        //get the detail information of userAccount
                        String pEmail = p.getString("Email");
                        String pPassword = p.getString("Password");
                        String pName;
                        String pLastname;

                        if(pEmail.equals(UsernameString) && pPassword.equals(PasswordString)){
                            pName = p.getString("Name");
                            pLastname = p.getString("Lastname");
                            pName = pName.substring(0,1).toUpperCase() + pName.substring(1);
                            pLastname = pLastname.substring(0,1).toUpperCase() + pLastname.substring(1);
                            NameLastname.setText(pName + " " + pLastname);
                            return;
                        } else {
                            NameLastname.setText("Name Lastname");
                        }

                    }
                } else {
                    System.out.println("Error::: in getNameandLastnameFromParse()!");

                }
            }
        });

    }

    private void getEventNumber() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("TestEvent");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> parseEvents, ParseException e) {
                if (e == null) {
                    int len = parseEvents.size();
                    int eventsNumber = 0;
                    for (int i = 0; i < len; i++) {
                        //get the i th element of events in order to obtain details
                        ParseObject p = parseEvents.get(i);
                        String pEvent_Creater = p.getString("Event_Creater");

                        //if Event creater is same as this user, show events. If not, do not show
                        if (pEvent_Creater.equals(ObjectIdOfUser)){
                            eventsNumber++;
                        }
                    }
                    EventsNumber.setText(Integer.toString(eventsNumber));
                }
            }
        });
    }

    private void getFriendNumber() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("FriendRequest");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> parseFriendRequest, ParseException e) {
                if (e == null) {
                    int len = parseFriendRequest.size();
                    //check for sender
                    for (int i = 0; i < len; i++) {
                        ParseObject p = parseFriendRequest.get(i);
                        String pSender = p.getString("Sender");
                        if (pSender.equals(ObjectIdOfUser)) {
                            String pReceiver = p.getString("Receiver");
                            if (!ObjectIdofFriendList.contains(pReceiver) && !pReceiver.equals(pSender)) {
                                ObjectIdofFriendList.add(pReceiver);
                            }
                        }
                    }
                    //check for receiver
                    for (int i = 0; i < len; i++) {
                        ParseObject p = parseFriendRequest.get(i);
                        String pReceiver = p.getString("Receiver");
                        if (pReceiver.equals(ObjectIdOfUser)) {
                            String pSender = p.getString("Sender");
                            if (!ObjectIdofFriendList.contains(pSender) && !pReceiver.equals(pSender)) {
                                ObjectIdofFriendList.add(pSender);
                            }
                        }
                    }
                    FriendNumber.setText(String.valueOf(ObjectIdofFriendList.size()));
                } else {
                    System.out.println("Error:......................getFriendList()");
                }
            }
        });

    }

    public void onLogOut (View view) {
        // Direct to log in screen
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
    }
}

package com.example.akin_.letseventapplication;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        TabHost tabHost3 = (TabHost) findViewById(R.id.tabHost3);
        LocalActivityManager mLocalActivityManager = new LocalActivityManager(this, false);
        mLocalActivityManager.dispatchCreate(savedInstanceState);
        tabHost3.setup(mLocalActivityManager);

        TabHost.TabSpec profile_MyEvents_Activity = tabHost3.newTabSpec("MyEvents");
        profile_MyEvents_Activity.setIndicator("My Events");
        profile_MyEvents_Activity.setContent(new Intent(this, Profile_MyEvents_Activity.class));

        TabHost.TabSpec profile_EventsToAttend_Activity = tabHost3.newTabSpec("EventsToAttend");
        profile_EventsToAttend_Activity.setIndicator("Events To Attend");
        profile_EventsToAttend_Activity.setContent(new Intent(this, Profile_EventsToAttend_Activity.class));

        TabHost.TabSpec profile_PastEvents_Activity = tabHost3.newTabSpec("PastEvents");
        profile_PastEvents_Activity.setIndicator("Past Events");
        profile_PastEvents_Activity.setContent(new Intent(this, Profile_PastEvents_Activity.class));

        tabHost3.addTab(profile_MyEvents_Activity);
        tabHost3.addTab(profile_EventsToAttend_Activity);
        tabHost3.addTab(profile_PastEvents_Activity);
    }
}

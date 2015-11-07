package com.example.akin_.letseventapplication;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

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
    }

    @Override
    public void onBackPressed() {
    }
}

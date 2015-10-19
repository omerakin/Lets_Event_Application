package com.example.akin_.letseventapplication;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class ActivitiesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);

        TabHost tabHost3 = (TabHost) findViewById(R.id.tabHost3);
        LocalActivityManager mLocalActivityManager = new LocalActivityManager(this, false);
        mLocalActivityManager.dispatchCreate(savedInstanceState);
        tabHost3.setup(mLocalActivityManager);

        TabHost.TabSpec activities_Followed_Activity = tabHost3.newTabSpec("Followed");
        activities_Followed_Activity.setIndicator("FOLLOWED");
        activities_Followed_Activity.setContent(new Intent(this, Activities_Followed_Activity.class));

        TabHost.TabSpec activities_You_Activity = tabHost3.newTabSpec("You");
        activities_You_Activity.setIndicator("YOU");
        activities_You_Activity.setContent(new Intent(this, Activities_You_Activity.class));

        tabHost3.addTab(activities_Followed_Activity);
        tabHost3.addTab(activities_You_Activity);
    }
}

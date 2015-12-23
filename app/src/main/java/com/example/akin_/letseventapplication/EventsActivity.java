package com.example.akin_.letseventapplication;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class EventsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);


        TabHost tabHost2 = (TabHost) findViewById(R.id.tabHost2);
        LocalActivityManager mLocalActivityManager = new LocalActivityManager(this, false);
        mLocalActivityManager.dispatchCreate(savedInstanceState);
        tabHost2.setup(mLocalActivityManager);

        TabHost.TabSpec event_Public_Activity = tabHost2.newTabSpec("Public");
        event_Public_Activity.setIndicator("PUBLIC");
        event_Public_Activity.setContent(new Intent(this, Event_Public_Activity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

        TabHost.TabSpec event_Friends_Activity = tabHost2.newTabSpec("Friends");
        event_Friends_Activity.setIndicator("FRIENDS");
        event_Friends_Activity.setContent(new Intent(this, Event_Friends_Activity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

        TabHost.TabSpec event_Private_Activity = tabHost2.newTabSpec("Private");
        event_Private_Activity.setIndicator("PRIVATE");
        event_Private_Activity.setContent(new Intent(this, Event_Private_Activity.class));

        tabHost2.addTab(event_Public_Activity);
        tabHost2.addTab(event_Friends_Activity);
        tabHost2.addTab(event_Private_Activity);
    }

    @Override
    public void onBackPressed() {
    }
}

package com.example.akin_.letseventapplication;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import com.parse.Parse;
import com.parse.ParseObject;


public class MainActivity extends AppCompatActivity {

    TabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "3I5oISFpVA1okn80tFZpSH4p2bVIa0NQFwoD8cdu", "PkHNBNLH6JLX50ZN0cI7xDps8OdVhXWwm7o7ZwC8");

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();


        tabHost = (TabHost) findViewById(R.id.tabHost);
        LocalActivityManager mLocalActivityManager = new LocalActivityManager(this, false);
        mLocalActivityManager.dispatchCreate(savedInstanceState);
        tabHost.setup(mLocalActivityManager);

        TabHost.TabSpec mapActivity = tabHost.newTabSpec("Map");
        mapActivity.setIndicator("");
        mapActivity.setContent(new Intent(this, MapActivity.class));

        TabHost.TabSpec eventActivity = tabHost.newTabSpec("Event");
        eventActivity.setIndicator("");
        eventActivity.setContent(new Intent(this, EventsActivity.class));

        TabHost.TabSpec activitiesActivity = tabHost.newTabSpec("Activities");
        activitiesActivity.setIndicator("");
        activitiesActivity.setContent(new Intent(this, ActivitiesActivity.class));

        TabHost.TabSpec profileActivity = tabHost.newTabSpec("Profile");
        profileActivity.setIndicator("");
        profileActivity.setContent(new Intent(this, ProfileActivity.class));

        tabHost.addTab(mapActivity);
        tabHost.addTab(eventActivity);
        tabHost.addTab(activitiesActivity);
        tabHost.addTab(profileActivity);


        tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.ic_map_gray);
        tabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.ic_events_gray);
        tabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.ic_activities_gray);
        tabHost.getTabWidget().getChildAt(3).setBackgroundResource(R.drawable.ic_profile_gray);

        tabHost.getTabWidget().setCurrentTab(0);
        tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.ic_map_white);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.ic_map_gray);
                tabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.ic_events_gray);
                tabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.ic_activities_gray);
                tabHost.getTabWidget().getChildAt(3).setBackgroundResource(R.drawable.ic_profile_gray);

                if(tabHost.getCurrentTab()==0){
                    tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundResource(R.drawable.ic_map_white);
                }else if (tabHost.getCurrentTab()==1){
                    tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundResource(R.drawable.ic_events_white);
                }else if (tabHost.getCurrentTab()==2){
                    tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundResource(R.drawable.ic_activities_white);
                }else {
                    tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundResource(R.drawable.ic_profile_white);
                }

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

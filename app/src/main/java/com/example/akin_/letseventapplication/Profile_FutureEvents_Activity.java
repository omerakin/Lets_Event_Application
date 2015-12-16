package com.example.akin_.letseventapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Profile_FutureEvents_Activity extends AppCompatActivity {

    private String ObjectIdOfUser;
    List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
    Calendar calendar = Calendar.getInstance();
    private boolean isParseStartDateGreaterThanCurrentDate = false;
    private boolean isParseEndDateGreaterThanCurrentDate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__future_events_);
        // Read ObjectId from file
        readObjectIdFromFile();

        //get Events from parse
        getMyEventsFromParse();
    }

    private void readObjectIdFromFile() {

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

        System.out.println("ObjectidInformation........." + ObjectId + "............");
    }

    private void getMyEventsFromParse() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("TestEvent");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> parseEvents, ParseException e) {
                if (e == null) {
                    int len = parseEvents.size();
                    for (int i = 0; i < len; i++) {
                        //get the i th element of events in order to obtain details
                        ParseObject p = parseEvents.get(i);
                        String pEvent_Creater = p.getString("Event_Creater");

                        //if Event creater is same as this user, show events. If not, do not show
                        if (pEvent_Creater.equals(ObjectIdOfUser)) {

                            compareParseStartDateAndCurrentDate(p);
                            compareParseEndDateAndCurrentDate(p);

                            if (isParseStartDateGreaterThanCurrentDate && isParseEndDateGreaterThanCurrentDate) {

                                //return them default values
                                isParseStartDateGreaterThanCurrentDate = false;
                                isParseEndDateGreaterThanCurrentDate = false;

                                //get the detail information of event
                                String pName = p.getString("Event_Name");
                                String pLocation = p.getString("Location");
                                String pDate = p.getString("Start_Date") + "  " + p.getString("Start_Time");
                                String pPicture = Integer.toString(R.drawable.other);

                                // set the picture of event
                                if (p.getString("Category_Name").equals("Birthday")) {
                                    pPicture = Integer.toString(R.drawable.birthday);
                                } else if (p.getString("Category_Name").equals("Concerts")) {
                                    pPicture = Integer.toString(R.drawable.concerts);
                                } else if (p.getString("Category_Name").equals("Conferences")) {
                                    pPicture = Integer.toString(R.drawable.conference);
                                } else if (p.getString("Category_Name").equals("Comedy Events")) {
                                    pPicture = Integer.toString(R.drawable.comedyevents);
                                } else if (p.getString("Category_Name").equals("Education")) {
                                    pPicture = Integer.toString(R.drawable.education);
                                } else if (p.getString("Category_Name").equals("Family")) {
                                    pPicture = Integer.toString(R.drawable.family);
                                } else if (p.getString("Category_Name").equals("Festivals")) {
                                    pPicture = Integer.toString(R.drawable.festivals);
                                } else if (p.getString("Category_Name").equals("Film")) {
                                    pPicture = Integer.toString(R.drawable.film);
                                } else if (p.getString("Category_Name").equals("Food - Wine")) {
                                    pPicture = Integer.toString(R.drawable.food_wine);
                                } else if (p.getString("Category_Name").equals("Fundraising - Charity")) {
                                    pPicture = Integer.toString(R.drawable.fundraising);
                                } else if (p.getString("Category_Name").equals("Art Galleries - Exhibits")) {
                                    pPicture = Integer.toString(R.drawable.exhibits);
                                } else if (p.getString("Category_Name").equals("Health - Wellness")) {
                                    pPicture = Integer.toString(R.drawable.health);
                                } else if (p.getString("Category_Name").equals("Holiday Events")) {
                                    pPicture = Integer.toString(R.drawable.holiday);
                                } else if (p.getString("Category_Name").equals("Kids")) {
                                    pPicture = Integer.toString(R.drawable.kids);
                                } else if (p.getString("Category_Name").equals("Literary - Books")) {
                                    pPicture = Integer.toString(R.drawable.literary);
                                } else if (p.getString("Category_Name").equals("Museums - Attractions")) {
                                    pPicture = Integer.toString(R.drawable.museums);
                                } else if (p.getString("Category_Name").equals("Business - Networking")) {
                                    pPicture = Integer.toString(R.drawable.bussiness);
                                } else if (p.getString("Category_Name").equals("Nightlife - Singles")) {
                                    pPicture = Integer.toString(R.drawable.nightlife);
                                } else if (p.getString("Category_Name").equals("University - Alumni")) {
                                    pPicture = Integer.toString(R.drawable.university);
                                } else if (p.getString("Category_Name").equals("Organizations - Meetups")) {
                                    pPicture = Integer.toString(R.drawable.organizations);
                                } else if (p.getString("Category_Name").equals("Outdoors - Recreation")) {
                                    pPicture = Integer.toString(R.drawable.outdoors);
                                } else if (p.getString("Category_Name").equals("Performing Arts")) {
                                    pPicture = Integer.toString(R.drawable.performing);
                                } else if (p.getString("Category_Name").equals("Pets")) {
                                    pPicture = Integer.toString(R.drawable.pets);
                                } else if (p.getString("Category_Name").equals("Politics - Activism")) {
                                    pPicture = Integer.toString(R.drawable.politics);
                                } else if (p.getString("Category_Name").equals("Sales - Retail")) {
                                    pPicture = Integer.toString(R.drawable.retail);
                                } else if (p.getString("Category_Name").equals("Science")) {
                                    pPicture = Integer.toString(R.drawable.science);
                                } else if (p.getString("Category_Name").equals("Religion - Spirituality")) {
                                    pPicture = Integer.toString(R.drawable.religion);
                                } else if (p.getString("Category_Name").equals("Sports")) {
                                    pPicture = Integer.toString(R.drawable.sports);
                                } else if (p.getString("Category_Name").equals("Technology")) {
                                    pPicture = Integer.toString(R.drawable.teknoloji);
                                } else if (p.getString("Category_Name").equals("Tour Dates")) {
                                    pPicture = Integer.toString(R.drawable.tours);
                                } else if (p.getString("Category_Name").equals("Tradeshows")) {
                                    pPicture = Integer.toString(R.drawable.tradeshow);
                                } else {
                                    pPicture = Integer.toString(R.drawable.other);
                                }
                                //set other adjustments
                                afterQueryProcessing(pName, pLocation, pDate, pPicture);
                            }
                        }
                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());

                }
            }
        });
    }



    private void afterQueryProcessing(String pname, String plocation, String pdate, String ppicture) {

        // You can access m2Status here reliably,
        // assuming you only call this method
        // as shown above, but you should still
        // use defensive programming
        HashMap<String, String> hm = new HashMap<String,String>();
        hm.put("txt", pname);
        hm.put("cur","Date : " + pdate);
        hm.put("flag", ppicture );
        hm.put("pla", "Location : " + plocation);
        aList.add(hm);
// Keys used in Hashmap
        String[] from = {"flag", "txt", "cur", "pla"};

        // Ids of views in listview_layout
        int[] to = {R.id.flag, R.id.txt, R.id.cur, R.id.pla};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_layout, from, to);

        // Getting a reference to listview of main.xml layout file
        ListView listView = (ListView) findViewById(R.id.listView);

        // Setting the adapter to the listView
        listView.setAdapter(adapter);
    }

    private void compareParseStartDateAndCurrentDate(ParseObject p) {

        try {
            SimpleDateFormat simpleFormatter = new SimpleDateFormat("MM-dd-yyyy");
            //Parse date
            String pStart_Date = p.getString("Start_Date");
            Date parseStartDate = simpleFormatter.parse(pStart_Date);
            //Current date
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
            String currentDatesdf = simpleDateFormat.format(calendar.getTime());
            Date currentDate = simpleFormatter.parse(currentDatesdf);

            //compare them
            if (parseStartDate.compareTo(currentDate) > 0) {

                System.out.println("parseStartDate...."+pStart_Date+"currentDate...."+currentDatesdf);
                isParseStartDateGreaterThanCurrentDate = true;
                System.out.println("parse start date is greater");
            }
        } catch (java.text.ParseException e1) {
            e1.printStackTrace();
        }

    }

    private void compareParseEndDateAndCurrentDate(ParseObject p) {

        try {
            SimpleDateFormat simpleFormatter = new SimpleDateFormat("MM-dd-yyyy");
            //Parse date
            String pEnd_Date = p.getString("End_Date");
            Date parseEndDate = simpleFormatter.parse(pEnd_Date);
            //Current date
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
            String currentDatesdf = simpleDateFormat.format(calendar.getTime());
            Date currentDate = simpleFormatter.parse(currentDatesdf);

            //compare them
            if (parseEndDate.compareTo(currentDate) > 0) {

                System.out.println("parseEndDate...."+pEnd_Date+"currentDate...."+currentDatesdf);
                isParseEndDateGreaterThanCurrentDate = true;
                System.out.println("parse end date is greater");
            }
        } catch (java.text.ParseException e1) {
            e1.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
    }
}

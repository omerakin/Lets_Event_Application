package com.example.akin_.letseventapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Event_Friends_Activity extends AppCompatActivity {
    TextView info2;
    int arrayLength = 0;
    String[] eventNames ;
    int[] eventPictures ;
    String[] eventDates ;
    String[] eventLocation ;
    String ObjectIdOfUser;
    ArrayList<String> ObjectIdofFriendList;

    //hashmap li arraylist
    List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event__friends_);

        ObjectIdofFriendList = new ArrayList<>();

        readObjectIdOfUser();
        getFriendList();
        runQuery();

        // Keys used in Hashmap
        String[] from = {"flag", "txt", "cur", "pla"};

        // Ids of views in listview_layout
        int[] to = {R.id.flag, R.id.txt, R.id.cur, R.id.pla};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_layout, from, to);

        // Getting a reference to listview of main.xml layout file
        final ListView listView = (ListView) findViewById(R.id.listView4);

        // Setting the adapter to the listView
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String parseObj = aList.get(position).get("objectId");
                String pEDate = aList.get(position).get("cur");
                String pEName = aList.get(position).get("txt");
                String pELocation = aList.get(position).get("pla");
                String pEDescription = aList.get(position).get("eventDescription");
                String pECreator = aList.get(position).get("eventCreator");
                String pEType = aList.get(position).get("eventType");
                String pEPicture = aList.get(position).get("flag");

                Object asd = listView.getItemAtPosition(position);
                Intent intent = new Intent(Event_Friends_Activity.this, EventDescriptionActivity.class);
                intent.putExtra("name", Integer.toString(position));
                intent.putExtra("pEDate", pEDate);
                intent.putExtra("pEName", pEName);
                intent.putExtra("pELocation", pELocation);
                intent.putExtra("pEDescription", pEDescription);
                intent.putExtra("pECreator", pECreator);
                intent.putExtra("pEPicture", pEPicture);
                intent.putExtra("pEType", pEType);
                intent.putExtra("parseObjectId", parseObj);
                startActivity(intent);

            }

        });

    }

    private void readObjectIdOfUser() {
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

    private void getFriendList() {
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
                } else {
                    System.out.println("Error:..............getFriendList()");
                }
            }
        });
    }

    private void runQuery() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("TestEvent");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> parseEvents, ParseException e) {
                if (e == null) {
                    int len = parseEvents.size();
                    for (int i = 0; i < len; i++) {
                        //get the i th element of events in order to obtain details
                        ParseObject p = parseEvents.get(i);
                        String pEventCreatorCheck = p.getString("Event_Creater");
                        for (int j=0; j < ObjectIdofFriendList.size(); j++) {
                            if (ObjectIdofFriendList.get(j).equals(pEventCreatorCheck)) {
                                //get the detail information of event
                                String pName = p.getString("Event_Name");
                                String pLocation = p.getString("Location");
                                String pDate = p.getString("Start_Date") + "  " + p.getString("Start_Time");
                                String pEventObjectId = p.getObjectId();
                                String pEventDescription = p.getString("Description");
                                String pEventCreator = p.getString("Event_Creater");
                                String pEventType = p.getString("Category_Name");
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
                                afterQueryProcessing(pName, pLocation, pDate, pPicture, pEventObjectId, pEventDescription, pEventCreator, pEventType);
                            }
                        }
                    }
                } else {
                    System.out.println("Error:.............................runQuery()");

                }
            }
        });

    }

    private void afterQueryProcessing(String pname, String plocation, String pdate, String ppicture, String pEventObjectId,
                                      String pEventDescription, String pEventCreator, String pEventType) {
        // You can access m2Status here reliably,
        // assuming you only call this method
        // as shown above, but you should still
        // use defensive programming
        HashMap<String, String> hm = new HashMap<String,String>();
        hm.put("txt", pname);
        hm.put("cur","Date : " + pdate);
        hm.put("flag", ppicture );
        hm.put("pla", "Location : " + plocation);


        hm.put("eventDescription", pEventDescription);
        hm.put("eventCreator", pEventCreator);
        hm.put("eventType", pEventType);
        hm.put("objectId", pEventObjectId);
        aList.add(hm);
// Keys used in Hashmap
        String[] from = {"flag", "txt", "cur", "pla"};

        // Ids of views in listview_layout
        int[] to = {R.id.flag, R.id.txt, R.id.cur, R.id.pla};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_layout, from, to);

        // Getting a reference to listview of main.xml layout file
        ListView listView = (ListView) findViewById(R.id.listView4);

        // Setting the adapter to the listView
        listView.setAdapter(adapter);
    }
    @Override
    public void onBackPressed() {
    }
}

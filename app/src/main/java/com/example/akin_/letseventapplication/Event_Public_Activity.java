package com.example.akin_.letseventapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Event_Public_Activity extends AppCompatActivity {
    TextView info2;
    int arrayLength = 0;
    // Array of strings storing country names
    String[] eventNames ;

    // Array of integers points to images stored in /res/drawable-ldpi/
    int[] eventPictures ;

    // Array of strings to store currencies
    String[] eventDates ;
    String[] eventLocation ;

    //hashmap li arraylist
    List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event__public_);



        runQuery();



        info2 = (TextView)findViewById(R.id.info2);
        Bundle params = new Bundle();
         params.putString("type", "event");
         params.putString("q", "sarıyer");
        //  params.putString("fields", "email");
       // params.putString ("fields", "first_name, last_name, email");
        //params.putString("fields", "last_name");
            /* make the API call */
        if(AccessToken.getCurrentAccessToken() != null) {
            new GraphRequest(
                    AccessToken.getCurrentAccessToken(),
                    "/search",
                    params,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
            /* handle the result */
                            //    info2.setText(info2.getText() + response.toString());
                            JSONObject obj = response.getJSONObject();
                            JSONArray arr;
                            JSONObject oneByOne;
                            JSONObject oneByOneChild;

                            try {
                                arr = obj.getJSONArray("data");
                                arrayLength = arr.length();
                                //oneByOne = arr.getJSONObject(0);
                                //info2.setText( arr.length() + "   "+ oneByOne.optString("name"));

                                eventNames = new String[arrayLength];
                                eventDates = new String[arrayLength];
                                eventPictures = new int[arrayLength];
                                eventLocation = new String[arrayLength];

                                for (int i = 0; i < arrayLength; i++) {
                                    oneByOne = arr.getJSONObject(i);
                                    eventNames[i] = oneByOne.optString("name");
                                    eventDates[i] = oneByOne.optString("start_time");
                                    eventPictures[i] = R.drawable.profile_pic;
                                    oneByOneChild = oneByOne.getJSONObject("place");
                                    eventLocation[i] = oneByOneChild.optString("name");
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                            // Each row in the list stores country name, currency and flag


                            for (int i = 0; i < arrayLength; i++) {
                                HashMap<String, String> hm = new HashMap<String, String>();
                                hm.put("txt", "Name : " + eventNames[i]);
                                hm.put("cur", "Date : " + eventDates[i]);
                                hm.put("flag", Integer.toString(eventPictures[i]));
                                hm.put("pla", "Location : " + eventLocation[i]);
                                aList.add(hm);
                            }


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
                    }
            ).executeAsync();
        }else{



            // Keys used in Hashmap
            String[] from = {"flag", "txt", "cur", "pla"};

            // Ids of views in listview_layout
            int[] to = {R.id.flag, R.id.txt, R.id.cur, R.id.pla};

            // Instantiating an adapter to store each items
            // R.layout.listview_layout defines the layout of each item
            SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_layout, from, to);

            // Getting a reference to listview of main.xml layout file
            final ListView listView = (ListView) findViewById(R.id.listView);

            // Setting the adapter to the listView
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    int posbug = position;
                    Object asd = listView.getItemAtPosition(position);
                    Intent intent = new Intent(Event_Public_Activity.this, EventDescriptionActivity.class);
                    intent.putExtra("name", Integer.toString(position));
                    startActivity(intent);

                }

            });
        }



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
    

    @Override
    public void onBackPressed() {
    }
}

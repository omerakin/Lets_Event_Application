package com.example.akin_.letseventapplication;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class EventDescriptionActivity extends AppCompatActivity {
    TextView tww;
    TextView info3;
    Button buttonComment;
    int arrayLength = 0;
    String eventID;
    String[] commentUser ;
    String[] commentCreationDate ;
    String[] commentComment ;
    ListView listviewActions;
    List<ParseObject> ob;
    ListViewAdapter_Comments adapter;
    private List<Comment2_Class> userComments = null;
    String eventObjectId;
    String eventCreator;

    //Textviews of events declare
    TextView dEventDateLabel;
    TextView dEventLocationLabel;
    TextView dEventDescriptionLabel;
    TextView dEventCreatorLabel;
    TextView dEventTypeLabel;
    ImageView dEventPicture;

    EditText commentBox;

    String NameLastname;
    String ObjectIdOfUser;

    List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_description);
        Intent intent = getIntent();
        tww = (TextView)findViewById(R.id.dEventNameLabel);
        info3 =(TextView)findViewById(R.id.info3);
        buttonComment = (Button)findViewById(R.id.buttonComment);
        eventObjectId = intent.getStringExtra("parseObjectId");
        commentBox = (EditText)findViewById(R.id.commentBox);

        dEventDateLabel = (TextView)findViewById(R.id.dEventDateLabel);
        dEventLocationLabel = (TextView)findViewById(R.id.dEventLocationLabel);
        dEventDescriptionLabel = (TextView)findViewById(R.id.dEventDescriptionLabel);
        dEventCreatorLabel = (TextView)findViewById(R.id.dEventCreatorLabel);
        dEventTypeLabel = (TextView)findViewById(R.id.dEventTypeLabel);
        eventCreator =intent.getStringExtra("pECreator");

        dEventDateLabel.setText(intent.getStringExtra("pEDate"));
        dEventLocationLabel.setText(intent.getStringExtra("pELocation"));
        dEventDescriptionLabel.setText(dEventDescriptionLabel.getText() +" "+ intent.getStringExtra("pEDescription") );
        //dEventCreatorLabel.setText(dEventCreatorLabel.getText() + " " + eventCreator);
        dEventTypeLabel.setText(dEventTypeLabel.getText() +" "+ intent.getStringExtra("pEType") );
        tww.setText(tww.getText() +" "+ intent.getStringExtra("pEName") );

        dEventPicture = (ImageView)findViewById(R.id.imageView);
        String mDrawableName = intent.getStringExtra("pEPicture");
        int dEpic =  Integer.parseInt(mDrawableName);
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), dEpic);
        dEventPicture.setImageDrawable(drawable);
        readObjectIdFromFile();

        //runQuery();
        new RemoteDataTask().execute();

    }

    // create remotedatatask asynck task
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... params){
            //create arraylist
            userComments = new ArrayList<Comment2_Class>();
            try {
                // Locate the class table named "Country" in Parse.com
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Comments");
                // Locate the column named "CommentID" in Parse.com and order list
                // by ascending
                // query.orderByAscending("CommentID");
                query.whereEqualTo("Event",eventObjectId);
                ob = query.find();
                for (ParseObject pAction : ob) {
                        Comment2_Class myAction = new Comment2_Class();
                        myAction.setUser((String) pAction.get("User"));
                        myAction.setCreatedAt(pAction.getCreatedAt().toString());
                        myAction.setComment((String) pAction.get("Comment"));


                    userComments.add(myAction);

                }
                ParseObject temp;
                ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("TestAccount");
                temp = query2.get(eventCreator);
                eventCreator = temp.get("Name") + " "+ temp.get("Lastname");

                ParseQuery<ParseObject> query3 = new ParseQuery<ParseObject>("TestAccount");
                temp = query2.get(ObjectIdOfUser);
                NameLastname = temp.get("Name") + " "+ temp.get("Lastname");


            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            dEventCreatorLabel.setText("Creator: " + " " + eventCreator);
            // Locate the listview in listview .xml
            listviewActions = (ListView) findViewById(R.id.listView2);
            // Pass the results into ListViewAdapter.java
            adapter = new ListViewAdapter_Comments(EventDescriptionActivity.this,
                    userComments);
            // Binds the Adapter to the ListView
            listviewActions.setAdapter(adapter);
        }


    }

    private void runQuery() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Comments");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> parseComments, ParseException e) {
                if (e == null) {
                    int len = parseComments.size();
                    for (int i = 0; i < len; i++) {
                        //get the i th element of comment in order to obtain details
                        ParseObject p = parseComments.get(i);

                        //get the detail information of comment
                        String pName = p.getString("User");
                        String pComment = p.getString("Comment");
                        String pDate = p.getCreatedAt().toString();
                        String pNumber = p.getString("CommentID");
                        //set other adjustments
                        afterQueryProcessing(pName, pComment, pDate, pNumber);

                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());

                }
            }
        });

    }

    private void afterQueryProcessing(String pname, String pcomment, String pdate, String pnumber) {
        // You can access m2Status here reliably,
        // assuming you only call this method
        // as shown above, but you should still
        // use defensive programming
        HashMap<String, String> hm = new HashMap<String,String>();
        hm.put("commentUserName", pname);
        hm.put("commentCreated"," Created At : " + pdate);
        hm.put("commentText", pnumber + "  :  " + pcomment);
        aList.add(hm);
        // Keys used in Hashmap
        String[] from = {"commentUserName", "commentCreated", "commentText", ""};

        // Ids of views in listview_layout
        int[] to = {R.id.commentUserName, R.id.commentCreated, R.id.commentText};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_layout, from, to);

        // Getting a reference to listview of main.xml layout file
        ListView listView = (ListView) findViewById(R.id.listView2);

        // Setting the adapter to the listView
        listView.setAdapter(adapter);

        info3.setText(info3.getText() + pname + "--" + pdate + "--" + pcomment + "--");
    }

    public void commentPressed(View view){
        String asd;
        ParseObject testAccount = new ParseObject("Comments");
        testAccount.put("User", NameLastname);
        testAccount.put("Event", eventObjectId);
        testAccount.put("Comment", String.valueOf(commentBox.getText()));
        testAccount.put("CommentID", String.valueOf(listviewActions.getAdapter().getCount()));
        testAccount.saveInBackground();

        ParseObject userAct = new ParseObject("UserActions");
        userAct.put("By", ObjectIdOfUser);
        userAct.put("To", eventObjectId);
        userAct.put("Type", "Comment");
        userAct.saveInBackground();
        commentBox.setText("");
        new RemoteDataTask().execute();
    }

    public void attendPressed(View view){

        ParseObject testAccount = new ParseObject("Comments");
        testAccount.put("User", NameLastname);
        testAccount.put("Event", eventObjectId);
        testAccount.put("isAttended", "Yes");
        testAccount.saveInBackground();

        ParseObject userAct = new ParseObject("UserActions");
        userAct.put("By", ObjectIdOfUser);
        userAct.put("To", eventObjectId);
        userAct.put("Type", "Attend");
        userAct.saveInBackground();
        commentBox.setText("");
        new RemoteDataTask().execute();
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

}

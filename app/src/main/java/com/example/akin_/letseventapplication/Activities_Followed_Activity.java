package com.example.akin_.letseventapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
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
import java.util.List;

public class Activities_Followed_Activity extends AppCompatActivity {
    // Declare Variables
    ListView listviewActionsFollowed;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    ListViewAdapter_Requests adapter;
    List<ParseObject> obTest;
    ArrayList<String> arrayListPicture;
    String ObjectIdOfUser;
    ArrayList<String> ObjectIdofFriendList;

    private List<UserActions_Class> userActionsFollowed = null;
    private TextView textViewLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities__followed_);
        ObjectIdofFriendList = new ArrayList<>();
        //remote datatask async parse
        new  RemoteDataTask().execute();
    }
    // create remotedatatask asynck task
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            // Create a progressdialog
           /* mProgressDialog = new ProgressDialog(Activities_You_Activity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Parse.com Actions");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog*/
        }
        @Override
        protected Void doInBackground(Void... params){

            readObjectIdOfUser();
            getFriendList();
            userActionsFollowed = new ArrayList<UserActions_Class>();
            arrayListPicture = new ArrayList<String>();
            try {
                // Locate the class table named "Country" in Parse.com
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("UserActions");
                // Locate the column named "CommentID" in Parse.com and order list
                // by ascending
                // query.orderByAscending("CommentID");
                ob = query.find();
                for (ParseObject pAction : ob) {
                    for (int j=0; j<ObjectIdofFriendList.size();j++) {
                        if (ObjectIdofFriendList.get(j).equals(pAction.get("By")==true
                                ||  ObjectIdofFriendList.get(j).equals(pAction.get("To"))==true)) {
                            UserActions_Class myAction = new UserActions_Class();
                            myAction.setTypeOfAction((String) pAction.get("Type"));
                            myAction.setActionBy((String) pAction.get("By"));
                            myAction.setActionTo((String) pAction.get("To"));
                            //Pasre object ids transformed to names

                            ParseObject temp;
                            ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("TestAccount");
                            temp = query2.get(myAction.getActionBy());
                            String deneme = (String) temp.get("Sex");
                            arrayListPicture.add((String) temp.get("Sex"));
                            myAction.setActionBy( temp.get("Name") + " "+ temp.get("Lastname"));

                            if(myAction.getTypeOfAction().equals("FriendRequest")) {
                                ParseQuery<ParseObject> query3 = new ParseQuery<ParseObject>("TestAccount");
                                temp = query3.get(myAction.getActionTo());
                                myAction.setActionTo(temp.get("Name") + " "+ temp.get("Lastname"));
                            }else {
                                ParseQuery<ParseObject> query4 = new ParseQuery<ParseObject>("TestEvent");
                                temp = query4.get(myAction.getActionTo());
                                myAction.setActionTo((String)temp.get("Event_Name"));

                            }
                            //add to list
                            userActionsFollowed.add(myAction);
                        }

                    }
                }
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            // Locate the listview in listview .xml
            listviewActionsFollowed = (ListView) findViewById(R.id.listviewActionsFollowed);
            // Pass the results into ListViewAdapter.java
            adapter = new ListViewAdapter_Requests(Activities_Followed_Activity.this,
                    userActionsFollowed, arrayListPicture);
            // Binds the Adapter to the ListView
            listviewActionsFollowed.setAdapter(adapter);
            textViewLoading = (TextView) findViewById(R.id.loading);
            textViewLoading.setText("");
        }


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

    @Override
    public void onBackPressed() {
    }
}

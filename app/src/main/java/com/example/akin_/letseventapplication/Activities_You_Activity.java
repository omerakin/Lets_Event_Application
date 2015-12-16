package com.example.akin_.letseventapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Activities_You_Activity extends AppCompatActivity {
    // Declare Variables
    ListView listviewActions;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    ListViewAdapter_Requests adapter;
    private List<UserActions_Class> userActions = null;
    String ObjectIdFromFile;
    List<ParseObject> obTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities__you_);
        //remote datatask async parse
        readObjectIdFromFile();
        new  RemoteDataTask().execute();
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

        ObjectIdFromFile = ObjectId.toString();
    }

    // create remotedatatask asynck task
    private class RemoteDataTask extends AsyncTask<Void, Void, Void>{
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
            //create arraylist
            userActions = new ArrayList<UserActions_Class>();
            try {
                // Locate the class table named "Country" in Parse.com
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("UserActions");
                // Locate the column named "CommentID" in Parse.com and order list
                // by ascending
               // query.orderByAscending("CommentID");
                ob = query.find();

                for (ParseObject pAction : ob) {
                    if (ObjectIdFromFile.equals(pAction.getString("By")) || ObjectIdFromFile.equals(pAction.getString("To"))) {
                        UserActions_Class myAction = new UserActions_Class();
                        myAction.setTypeOfAction((String) pAction.get("Type"));
                        myAction.setActionBy((String) pAction.get("By"));
                        myAction.setActionTo((String) pAction.get("To"));


                        //Pasre object ids transformed to names

                        ParseObject temp;
                        ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("TestAccount");
                        temp = query2.get(myAction.getActionBy());
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

                        userActions.add(myAction);
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
            listviewActions = (ListView) findViewById(R.id.listviewActions);
            // Pass the results into ListViewAdapter.java
            adapter = new ListViewAdapter_Requests(Activities_You_Activity.this,
                    userActions);
            // Binds the Adapter to the ListView
            listviewActions.setAdapter(adapter);
        }


    }
    @Override
    public void onBackPressed() {
    }
}

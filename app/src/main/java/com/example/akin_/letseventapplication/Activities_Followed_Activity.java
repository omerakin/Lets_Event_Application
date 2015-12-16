package com.example.akin_.letseventapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class Activities_Followed_Activity extends AppCompatActivity {
    // Declare Variables
    ListView listviewActionsFollowed;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    ListViewAdapter_Requests adapter;
    private List<UserActions_Class> userActionsFollowed = null;
    List<ParseObject> obTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities__followed_);
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
            //create arraylist
            userActionsFollowed = new ArrayList<UserActions_Class>();
            try {
                // Locate the class table named "Country" in Parse.com
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                        "UserActions");
                // Locate the column named "CommentID" in Parse.com and order list
                // by ascending
                // query.orderByAscending("CommentID");
                ob = query.find();
                for (ParseObject pAction : ob) {
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
                    userActionsFollowed.add(myAction);
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
                    userActionsFollowed);
            // Binds the Adapter to the ListView
            listviewActionsFollowed.setAdapter(adapter);
        }


    }

    @Override
    public void onBackPressed() {
    }
}

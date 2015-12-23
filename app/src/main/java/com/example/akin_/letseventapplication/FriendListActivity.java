package com.example.akin_.letseventapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FriendListActivity extends AppCompatActivity {

    private String ObjectIdOfUser;
    private ArrayList<String> ObjectIdofFriendList;
    private ListView FriendListListView;
    private ArrayList<String> arrayListPicture;
    private ListViewFriend_List adapter;
    private List<ParseObject> FriendParseObject;
    private TextView textViewLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        ObjectIdofFriendList = new ArrayList<>();
        FriendListListView = (ListView) findViewById(R.id.FriendList);
        arrayListPicture = new ArrayList<String>();

        new FriendTask().execute();
    }

    private class FriendTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            readObjectIdOfUser();
            getFriendList();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            adapter = new ListViewFriend_List(FriendListActivity.this,
                    ObjectIdofFriendList, arrayListPicture);
            FriendListListView.setAdapter(adapter);
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
        try {
            FriendParseObject = query.find();
            for (ParseObject pAction : FriendParseObject) {

                String pSender1 = (String) pAction.get("Sender");
                if (pSender1.equals(ObjectIdOfUser)) {
                    String pReceiver1 = (String) pAction.get("Receiver");
                    if (!ObjectIdofFriendList.contains(pReceiver1) && !pReceiver1.equals(pSender1)) {
                        ObjectIdofFriendList.add(pReceiver1);
                        arrayListPicture.add("Logo");
                    }
                }

                String pReceiver2 = (String) pAction.get("Receiver");
                if (pReceiver2.equals(ObjectIdOfUser)) {
                    String pSender2 = (String) pAction.get("Sender");
                    if (!ObjectIdofFriendList.contains(pSender2) && !pReceiver2.equals(pSender2)) {
                        ObjectIdofFriendList.add(pSender2);
                        arrayListPicture.add("Logo");
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}

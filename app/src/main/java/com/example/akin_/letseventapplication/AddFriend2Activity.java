package com.example.akin_.letseventapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class AddFriend2Activity extends AppCompatActivity {

    EditText addFriendEditText;
    Button addFriendButton;
    String friendString;
    String friendObjectId;
    String ObjectIdFromFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend2);
        addFriendButton = (Button) findViewById(R.id.addFriendButton);
        addFriendEditText = (EditText)findViewById(R.id.addFriendEditText);
        readObjectIdFromFile();

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

    private void runQuery(final String friendString) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("TestAccount");
        query.whereEqualTo("Email", friendString);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    Log.d("score", "The getFirst request failed.");
                    afterQueryProcessing("", "ssss");
                    // You don't have a good value to use, so figure
                    // out a way to handle that scenario
                } else {
                    friendObjectId = object.getObjectId();
                    Log.d("id", "Retrieved the object.");
                    afterQueryProcessing(ObjectIdFromFile,friendObjectId);
                }
            }
        });
    }

    private void afterQueryProcessing(String objID, String friendId) {

        if (objID.equals(friendId)) {
            new AlertDialog.Builder(this).setTitle("Warning").setMessage("You cant add yourself!").setNeutralButton("Close", null).show();

        }else if (objID != ""){
            ParseObject friendRequest = new ParseObject("FriendRequest");
            friendRequest.put("Sender", objID);
            friendRequest.put("Receiver", friendId);
            friendRequest.put("Result", "0");
            friendRequest.saveInBackground();

            ParseObject userAct = new ParseObject("UserActions");
            userAct.put("By", objID);
            userAct.put("To", friendId);
            userAct.put("Type", "FriendRequest");
            userAct.saveInBackground();
            new AlertDialog.Builder(this).setTitle("Warning").setMessage("Friend Successfully added, Now you can see what events they created in Friends Events Tab!").setNeutralButton("Close", null).show();
            //ParsePush.subscribeInBackground(friendString);
            ParseInstallation installation = ParseInstallation.getCurrentInstallation();
            installation.addAllUnique("channels", Arrays.asList(friendString));
            installation.saveInBackground();
            ParsePush push = new ParsePush();
            push.setChannel(friendString);
            push.setMessage("A added " + friendString);
            push.sendInBackground();

        } else {
              new AlertDialog.Builder(this).setTitle("Warning").setMessage("Incorrect Email!").setNeutralButton("Close", null).show();
                 }


}

    public void onAddFriend(View view){
        friendString = String.valueOf(addFriendEditText.getText());

        if (friendString == null) {
            new AlertDialog.Builder(this).setTitle("Warning").setMessage("Empty Email!").setNeutralButton("Close", null).show();

        }else{
           runQuery(friendString);
        }
    }
}

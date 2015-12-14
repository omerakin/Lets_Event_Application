package com.example.akin_.letseventapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

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


    List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_description);
        Intent intent = getIntent();
        tww = (TextView)findViewById(R.id.textView9);
        info3 =(TextView)findViewById(R.id.info3);
        buttonComment = (Button)findViewById(R.id.buttonComment);
        tww.setText(tww.getText()+ intent.getStringExtra("name"));
        runQuery();

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
        hm.put("commentText", pnumber + "  :  "+ pcomment);
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
        ParseObject testAccount = new ParseObject("Comments");
        testAccount.put("User", "deneme");
        testAccount.put("Event", "deneme");
        testAccount.put("Comment", "deneme");
        testAccount.put("CommentID", "15");
        testAccount.saveInBackground();
    }

}

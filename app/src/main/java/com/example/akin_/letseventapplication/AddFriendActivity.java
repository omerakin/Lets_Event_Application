package com.example.akin_.letseventapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class AddFriendActivity extends AppCompatActivity {
    ListView listView3;
    List<ParseObject> ob;
    ListViewAdapter_Requests adapter;
    private List<String> userActions = null;
    EditText searchPersonText;
    public ArrayAdapter<String> listAdapter;
    ArrayList<String> plistViewAdapterContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        searchPersonText = (EditText) findViewById(R.id.searchPersonText);


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
            userActions = new ArrayList<String>();
            try {
                // Locate the class table named "Country" in Parse.com
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("TestAccount");
                // Locate the column named "CommentID" in Parse.com and order list
                // by ascending
                // query.orderByAscending("CommentID");
                ob = query.find();

                for (ParseObject pAction : ob) {
                        String myAction = new String();
                        myAction = ((String) pAction.get("Name"));
                        userActions.add(myAction);
                        myAction = ((String) pAction.get("Lastname"));
                        userActions.add(myAction);
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
           // listView3 = (ListView) findViewById(R.id.listView3);
            // Pass the results into ListViewAdapter.java
          /*  adapter = new ListViewAdapter_Requests(AddFriendActivity.this,
                    userActions);*/
            // Binds the Adapter to the ListView

          //  listView3.setAdapter(adapter);
            ListView itemList = (ListView) findViewById(R.id.listView3);
            //runQuery();
            plistViewAdapterContent.add("asd");
            plistViewAdapterContent.add("sss");
            plistViewAdapterContent.add("ddddd");
            String[] listViewAdapterContent = parseNameSearchArray(plistViewAdapterContent);
           // String [] listViewAdapterContent = {"School", "House", "Building", "Food", "Sports", "Dress", "Ring"};

            listAdapter = new ArrayAdapter<String>(AddFriendActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, listViewAdapterContent);

            itemList.setAdapter(listAdapter);

            itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
// make Toast when click
                    Toast.makeText(getApplicationContext(), "Position " + position, Toast.LENGTH_LONG).show();
                    //buraya queri eklicen friend request atcan
                    //alarm manager notification manager oluşturcan
                    //ordan halledecen
                }
            });
            searchPersonText.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    AddFriendActivity.this.listAdapter.getFilter().filter(s);
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }


    }


    public void runQuery(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("TestAccount");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> parseAcc, ParseException e) {
                if (e == null) {
                    int len = parseAcc.size();
                    for (int i = 0; i < len; i++) {
                        //get the i th element of comment in order to obtain details
                        ParseObject p = parseAcc.get(i);

                        //get the detail information of comment
                        String pName = p.getString("Name");
                        String pLastname = p.getString("Lastname");
                        //set other adjustments
                        afterQueryProcessing(pName, pLastname);

                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());

                }
            }
        });

    }
    public void afterQueryProcessing(String pname, String plastname){
        plistViewAdapterContent.add(pname);
        plistViewAdapterContent.add(plastname);
    }

    public String[] parseNameSearchArray(ArrayList<String> palist){
        int lengthList = palist.size();
        String[] arrayTransfer = new  String[lengthList];

        for (int i=0; i<arrayTransfer.length; i++){
            arrayTransfer[i] = palist.get(i);
        }


        return arrayTransfer;
    }

}
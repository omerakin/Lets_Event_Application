package com.example.akin_.letseventapplication;

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

    EditText searchPersonText;
    private ArrayAdapter<String> listAdapter;
    ArrayList<String> listViewAdapterContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        searchPersonText = (EditText) findViewById(R.id.searchPersonText);

        ListView itemList = (ListView) findViewById(R.id.listView);


        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, listViewAdapterContent);

        itemList.setAdapter(listAdapter);

        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
// make Toast when click
                Toast.makeText(getApplicationContext(), "Position " + position, Toast.LENGTH_LONG).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);



    }

    public void runQuery(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("TestAcount");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> parseComments, ParseException e) {
                if (e == null) {
                    int len = parseComments.size();
                    for (int i = 0; i < len; i++) {
                        //get the i th element of comment in order to obtain details
                        ParseObject p = parseComments.get(i);

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
        listViewAdapterContent.add(pname);
        listViewAdapterContent.add(plastname);
    }


}
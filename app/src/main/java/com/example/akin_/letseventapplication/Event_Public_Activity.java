package com.example.akin_.letseventapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Event_Public_Activity extends AppCompatActivity {
    TextView info2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event__public_);

        info2 = (TextView)findViewById(R.id.info2);
        Bundle params = new Bundle();
         params.putString("type", "event");
         params.putString("q", "sarıyer");
        //  params.putString("fields", "email");
       // params.putString ("fields", "first_name, last_name, email");
        //params.putString("fields", "last_name");
            /* make the API call */
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/search",
                params,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
            /* handle the result */
                       // info2.setText(info2.getText() + response.toString());
                        JSONObject obj = response.getJSONObject();
                        JSONArray arr;
                        JSONObject oneByOne;

                        try{
                        arr = obj.getJSONArray("data");
                            oneByOne = arr.getJSONObject(1);

                            info2.setText( oneByOne.optString("description"));

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                    }
                }
        ).executeAsync();
    }


    @Override
    public void onBackPressed() {
    }
}

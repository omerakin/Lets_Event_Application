package com.example.akin_.letseventapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EventDescriptionActivity extends AppCompatActivity {
    TextView tww;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_description);
        Intent intent = getIntent();
        tww = (TextView)findViewById(R.id.textView9);
        tww.setText(tww.getText()+ intent.getStringExtra("name"));
    }
}

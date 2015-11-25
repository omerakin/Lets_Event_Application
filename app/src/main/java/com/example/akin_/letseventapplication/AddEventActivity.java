package com.example.akin_.letseventapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.parse.ParseObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEventActivity extends AppCompatActivity {

    private EditText event_name;
    private EditText location;
    private TextView Start_Date;
    private EditText Start_Time;
    private EditText End_Date;
    private EditText End_Time;
    private EditText description;
    final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        // get edit texts
        event_name = (EditText) findViewById(R.id.event_name);
        location = (EditText) findViewById(R.id.location);
        Start_Date = (TextView) findViewById(R.id.Start_Date);
        Start_Time = (EditText) findViewById(R.id.Start_Time);
        End_Date = (EditText) findViewById(R.id.End_Date);
        End_Time = (EditText) findViewById(R.id.End_Time);
        description = (EditText) findViewById(R.id.description);
    }

    DatePickerDialog.OnDateSetListener startDate = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setStartDateOnView();
        }
    };

    DatePickerDialog.OnDateSetListener endDate = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setEndDateOnView();
        }
    };

    TimePickerDialog.OnTimeSetListener startTime = new TimePickerDialog.OnTimeSetListener(){
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            setStartTimeOnView();
        }
    };

    TimePickerDialog.OnTimeSetListener endTime = new TimePickerDialog.OnTimeSetListener(){
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            setEndTimeOnView();
        }
    };

    public void setStartDateOnView() {
        String dateFormat = "MM-dd-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        Start_Date.setText( sdf.format(calendar.getTime()));
    }

    public void setEndDateOnView() {
        String dateFormat = "MM-dd-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        End_Date.setText( sdf.format(calendar.getTime()));
    }

    public void setStartTimeOnView() {
        String timeFormat = "hh:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat, Locale.US);
        Start_Time.setText( sdf.format(calendar.getTime()));
    }

    public void setEndTimeOnView() {
        String timeFormat = "hh:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat, Locale.US);
        End_Time.setText( sdf.format(calendar.getTime()));
    }

    public void onStartDate (View view) {
        new DatePickerDialog( AddEventActivity.this,
                startDate,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void onStartTime (View view) {
        new TimePickerDialog( AddEventActivity.this,
                startTime,
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE),
                false).show();
    }

    public void onEndDate (View view) {
        new DatePickerDialog( AddEventActivity.this,
                endDate,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void onEndTime (View view) {
        new TimePickerDialog( AddEventActivity.this,
                endTime,
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE),
                false).show();
    }

    public void onAddEvent (View view) {

        // getting Et as a string
        String event_nameET = String.valueOf(event_name.getText());
        String locationET = String.valueOf(location.getText());
        String Start_DateET = String.valueOf(Start_Date.getText());
        String Start_TimeET = String.valueOf(Start_Time.getText());
        String End_DateET = String.valueOf(End_Date.getText());
        String End_TimeET = String.valueOf(End_Time.getText());
        String descriptionET = String.valueOf(description.getText());

        //save to the Parse
        ParseObject testAccount = new ParseObject("TestEvent");
        testAccount.put("Event_Name", event_nameET);
        testAccount.put("Location", locationET);
        testAccount.put("Start_Date", Start_DateET);
        testAccount.put("Start_Time", Start_TimeET);
        testAccount.put("End_Date", End_DateET);
        testAccount.put("End_Time", End_TimeET);
        testAccount.put("Description", descriptionET);
        testAccount.saveInBackground();

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}

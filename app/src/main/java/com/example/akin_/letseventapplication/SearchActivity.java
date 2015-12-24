package com.example.akin_.letseventapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {

    private EditText event_name;
    private AutoCompleteTextView location;
    private EditText Start_Date;
    private EditText Start_Time;
    private EditText End_Date;
    private EditText End_Time;
    private EditText description;
    final Calendar calendar = Calendar.getInstance();
    Spinner spinnerCategory, spinnerType;
    ArrayAdapter<CharSequence> arrayAdapterCategory, arrayAdapterType;
    private double LatitudeOfLocation;
    private double LongitudeOfLocation;
    private String ObjectIdOfUser;

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    private static final String API_KEY = "AIzaSyCCsZCqddqtZ8xWeeQEU2TdBu0Q3NlYU0E";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // get edit texts
        event_name = (EditText) findViewById(R.id.event_name);
        location = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextLocation);
        Start_Date = (EditText) findViewById(R.id.Start_Date);
        Start_Time = (EditText) findViewById(R.id.Start_Time);
        End_Date = (EditText) findViewById(R.id.End_Date);
        End_Time = (EditText) findViewById(R.id.End_Time);
        description = (EditText) findViewById(R.id.description);

        //spinnerCategory
        spinnerCategory = (Spinner) findViewById(R.id.category);
        spinnerCategory.setSelection(0);
        arrayAdapterCategory = ArrayAdapter.createFromResource(this, R.array.category_names, R.layout.spinner_item);
        spinnerCategory.setAdapter(arrayAdapterCategory);
        //spinnerType
        spinnerType = (Spinner) findViewById(R.id.type);
        spinnerType.setSelection(0);
        arrayAdapterType = ArrayAdapter.createFromResource(this, R.array.type_names, R.layout.spinner_item);
        spinnerType.setAdapter(arrayAdapterType);

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
        End_Time.setText(sdf.format(calendar.getTime()));
    }

    public void onStartDate (View view) {
        new DatePickerDialog( SearchActivity.this,
                startDate,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void onStartTime (View view) {
        new TimePickerDialog( SearchActivity.this,
                startTime,
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE),
                false).show();
    }

    public void onEndDate (View view) {
        new DatePickerDialog( SearchActivity.this,
                endDate,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void onEndTime (View view) {
        new TimePickerDialog( SearchActivity.this,
                endTime,
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE),
                false).show();
    }

    public void onSearchEvent (View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

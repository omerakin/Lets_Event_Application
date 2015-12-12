package com.example.akin_.letseventapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.parse.ParseObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEventActivity extends AppCompatActivity {

    private EditText event_name;
    private EditText location;
    private EditText Start_Date;
    private EditText Start_Time;
    private EditText End_Date;
    private EditText End_Time;
    private EditText description;
    final Calendar calendar = Calendar.getInstance();
    Spinner spinnerCategory, spinnerType;
    ArrayAdapter<CharSequence> arrayAdapterCategory, arrayAdapterType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        // get edit texts
        event_name = (EditText) findViewById(R.id.event_name);
        location = (EditText) findViewById(R.id.location);
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
        String CategoryName = spinnerCategory.getSelectedItem().toString();
        String TypeName = spinnerType.getSelectedItem().toString();
        String descriptionET = String.valueOf(description.getText());


        //check the validatiy of fields
        if (event_nameET.trim().length()==0){
            new AlertDialog.Builder(this).setTitle("Warning").setMessage("Please fill the event name!").setNeutralButton("Close", null).show();
        } else if (locationET.trim().length()==0){
            new AlertDialog.Builder(this).setTitle("Warning").setMessage("Please fill the location!").setNeutralButton("Close", null).show();
        } else if (Start_DateET.trim().length()==0) {
            new AlertDialog.Builder(this).setTitle("Warning").setMessage("Please fill the start date!").setNeutralButton("Close", null).show();
        } else if (Start_TimeET.trim().length()==0) {
            new AlertDialog.Builder(this).setTitle("Warning").setMessage("Please fill the start time!").setNeutralButton("Close", null).show();
        } else if (End_DateET.trim().length()==0) {
            new AlertDialog.Builder(this).setTitle("Warning").setMessage("Please fill the end date!").setNeutralButton("Close", null).show();
        } else if (End_TimeET.trim().length()==0) {
            new AlertDialog.Builder(this).setTitle("Warning").setMessage("Please fill the end time!").setNeutralButton("Close", null).show();
        } else if (CategoryName.equals(spinnerCategory.getItemAtPosition(0))) {
            new AlertDialog.Builder(this).setTitle("Warning").setMessage("Please select a suitable category name!").setNeutralButton("Close", null).show();
        } else if (TypeName.equals(spinnerType.getItemAtPosition(0))) {
            new AlertDialog.Builder(this).setTitle("Warning").setMessage("Please select a suitable type name!").setNeutralButton("Close", null).show();
        } else if (descriptionET.trim().length()==0) {
            new AlertDialog.Builder(this).setTitle("Warning").setMessage("Please fill the description!").setNeutralButton("Close", null).show();
        } else {

            //save to the Parse
            ParseObject testAccount = new ParseObject("TestEvent");
            testAccount.put("Event_Name", event_nameET);
            testAccount.put("Location", locationET);
            testAccount.put("Start_Date", Start_DateET);
            testAccount.put("Start_Time", Start_TimeET);
            testAccount.put("End_Date", End_DateET);
            testAccount.put("End_Time", End_TimeET);
            testAccount.put("Category_Name", CategoryName);
            testAccount.put("Type_Name", TypeName);
            testAccount.put("Description", descriptionET);
            testAccount.saveInBackground();

            // Show added message to user
            new AlertDialog.Builder(this).setTitle("Congratulations").setMessage("Your event is successfully added !").setNeutralButton("Continue", null).show();

            //convert them to default value
            event_name.setText("");
            location.setText("");
            Start_Date.setText("");
            Start_Time.setText("");
            End_Date.setText("");
            End_Time.setText("");
            spinnerCategory.setSelection(0);
            spinnerType.setSelection(0);
            description.setText("");

            //Direct user to the my events tab
            MainActivity parentActivity;
            parentActivity = (MainActivity) this.getParent();
            parentActivity.switchTab(4);

            /*
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            */
        }

    }
}

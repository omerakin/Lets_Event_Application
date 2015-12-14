package com.example.akin_.letseventapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddEventActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

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

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    private static final String API_KEY = "AIzaSyCCsZCqddqtZ8xWeeQEU2TdBu0Q3NlYU0E";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        // get edit texts
        event_name = (EditText) findViewById(R.id.event_name);
        location = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextLocation);
        Start_Date = (EditText) findViewById(R.id.Start_Date);
        Start_Time = (EditText) findViewById(R.id.Start_Time);
        End_Date = (EditText) findViewById(R.id.End_Date);
        End_Time = (EditText) findViewById(R.id.End_Time);
        description = (EditText) findViewById(R.id.description);

        //AutoComplete TextView
        AutoCompleteTextView autoCompleteTextViewLocation = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextLocation);
        autoCompleteTextViewLocation.setAdapter(new GooglePlacesAutocompleteAdapter(this, R.layout.activity_add_event_location_list));
        autoCompleteTextViewLocation.setOnItemClickListener(this);

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

        //get the latitude and longitude of location
        getLatitudeLongitudeOfLocation(locationET);

        System.out.println("LatitudeOfLocation........" + LatitudeOfLocation + "...................");
        System.out.println("LongitudeOfLocation........" + LongitudeOfLocation + "...................");

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
            testAccount.put("Latitude", LatitudeOfLocation);
            testAccount.put("Longitude", LongitudeOfLocation);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String str = (String) parent.getItemAtPosition(position);
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public static ArrayList<String> autocomplete(String input) {
        ArrayList<String> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
            sb.append("&components=country:tr");
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());

            System.out.println("URL: "+url);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            return resultList;
        } catch (IOException e) {
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {

            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList<String>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                System.out.println("============================================================");
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
            }
        } catch (JSONException e) {
        }

        return resultList;
    }

    class GooglePlacesAutocompleteAdapter extends ArrayAdapter<String> implements Filterable {
        private ArrayList<String> resultList;

        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return resultList.get(index);
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        // Retrieve the autocomplete results.
                        resultList = autocomplete(constraint.toString());

                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }

    private void getLatitudeLongitudeOfLocation(String locationET) {

        if(Geocoder.isPresent()){
            try {
                Geocoder gc = new Geocoder(getBaseContext());
                List<Address> addresses= gc.getFromLocationName(locationET, 1); // get the found Address Objects
                List<LatLng> ll = new ArrayList<LatLng>(addresses.size()); // A list to save the coordinates if they are available
                for(Address a : addresses){
                    if(a.hasLatitude() && a.hasLongitude()){
                        ll.add(new LatLng(a.getLatitude(), a.getLongitude()));
                        LatitudeOfLocation = a.getLatitude();
                        LongitudeOfLocation = a.getLongitude();
                    }
                }
            } catch (IOException e) {
                // if there is no place that has Latitude and Longitude, set them to default values
                LatitudeOfLocation = 555.0;
                LongitudeOfLocation = 555.0;
            }
        }
    }

}

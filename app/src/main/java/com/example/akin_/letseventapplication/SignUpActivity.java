package com.example.akin_.letseventapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText confirmpassword;
    private EditText name;
    private EditText lastname;
    private Spinner spinnersex;
    ArrayAdapter<CharSequence> arrayAdapterSex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confirmpassword = (EditText) findViewById(R.id.confirmpassword);
        name = (EditText) findViewById(R.id.name);
        lastname = (EditText) findViewById(R.id.lastname);
        spinnersex = (Spinner) findViewById(R.id.sex);
        spinnersex.setSelection(0);
        arrayAdapterSex = ArrayAdapter.createFromResource(this, R.array.sex_names, R.layout.spinner_item);
        spinnersex.setAdapter(arrayAdapterSex);
    }

    public void onRealSignup (View view) {

        // getting Et as a string
        final String emailET = String.valueOf(email.getText());
        String passwordET = String.valueOf(password.getText());
        String confirmpasswordET = String.valueOf(confirmpassword.getText());
        String nameET = String.valueOf(name.getText());
        String lastnameET = String.valueOf(lastname.getText());
        String CategoryName = spinnersex.getSelectedItem().toString();

        //check the validatiy of fields
        if (emailET.trim().length()==0){
            new AlertDialog.Builder(this).setTitle("Warning").setMessage("Please enter valid E-mail or username!").setNeutralButton("Close", null).show();
        } else if (passwordET.trim().length()==0){
            new AlertDialog.Builder(this).setTitle("Warning").setMessage("Please enter valid password!").setNeutralButton("Close", null).show();
        } else if (confirmpasswordET.trim().length()==0) {
            new AlertDialog.Builder(this).setTitle("Warning").setMessage("Wrong confirm password is entered!").setNeutralButton("Close", null).show();
        } else if (nameET.trim().length()==0) {
            new AlertDialog.Builder(this).setTitle("Warning").setMessage("Please enter valid name!").setNeutralButton("Close", null).show();
        } else if (lastnameET.trim().length()==0) {
            new AlertDialog.Builder(this).setTitle("Warning").setMessage("Please enter valid last name!").setNeutralButton("Close", null).show();
        } else if (!passwordET.equals(confirmpasswordET)) {
            new AlertDialog.Builder(this).setTitle("Warning").setMessage("Password and confirm password are not matched!").setNeutralButton("Close", null).show();
        } else if (CategoryName.equals(spinnersex.getItemAtPosition(0))) {
            new AlertDialog.Builder(this).setTitle("Warning").setMessage("Please select a gender!").setNeutralButton("Close", null).show();
        } else {
            //if every field is okey, then
            //save to the Parse
            ParseObject testAccount = new ParseObject("TestAccount");
            testAccount.put("Email", emailET);
            testAccount.put("Password", passwordET);
            testAccount.put("Name", nameET);
            testAccount.put("Lastname", lastnameET);
            testAccount.put("Sex", spinnersex);
            testAccount.saveInBackground();

            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }

    }
}

package com.example.akin_.letseventapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private CheckBox checkBox;
    private SharedPreferences login;
    private SharedPreferences.Editor loginEditor;
    private Boolean saveLogin;
    public  boolean isPasswordSameAsParse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "3I5oISFpVA1okn80tFZpSH4p2bVIa0NQFwoD8cdu", "PkHNBNLH6JLX50ZN0cI7xDps8OdVhXWwm7o7ZwC8");

        etUsername = (EditText) findViewById(R.id.usernameET);
        etPassword = (EditText) findViewById(R.id.passwordET);
        checkBox = (CheckBox) findViewById(R.id.remenberMe);
        login = getSharedPreferences("login", MODE_PRIVATE);
        loginEditor = login.edit();
        saveLogin = login.getBoolean("saveLogin", false);

        if (saveLogin == true) {
            etUsername.setText(login.getString("username", ""));
            etPassword.setText(login.getString("password", ""));
            checkBox.setChecked(true);
        }
    }


    public void onLogin(View view) {

        final String usernameET = String.valueOf(etUsername.getText());
        final String passwordET = String.valueOf(etPassword.getText());
        isPasswordSameAsParse = false;


        if((usernameET== null || usernameET.isEmpty())|| (passwordET== null || passwordET.isEmpty()) )
        {
            new AlertDialog.Builder(this).setTitle("Warning").setMessage("Please enter valid username and password!").setNeutralButton("Close", null).show();
        } else {

            ParseQuery<ParseObject> query = ParseQuery.getQuery("TestAccount");
            query.whereEqualTo("Email", usernameET);
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                public void done(ParseObject parseObject, ParseException e) {
                    if (parseObject == null) {
                        Log.d("Email", "The getFirst request failed.");
                    } else {
                        String passwordFromParse = parseObject.getString("Password");
                        if (passwordET.equals(passwordFromParse)) {
                            if (checkBox.isChecked()) {
                                loginEditor.putBoolean("saveLogin", true);
                                loginEditor.putString("username", usernameET);
                                loginEditor.putString("password", passwordET);
                                loginEditor.commit();
                            } else {
                                loginEditor.clear();
                                loginEditor.commit();
                            }
                            isPasswordSameAsParse = true;
                        }
                    }
                }
            });

            if(isPasswordSameAsParse){
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("Username", usernameET);
                intent.putExtra("Password", passwordET);
                startActivity(intent);
            } else {
                new AlertDialog.Builder(this).setTitle("Warning").setMessage("Incorrect Email or Password!").setNeutralButton("Close", null).show();
            }



        }
    }

    public void onSignup (View view) {

        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);

    }
}

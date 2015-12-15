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
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private CheckBox checkBox;
    private SharedPreferences login;
    private SharedPreferences.Editor loginEditor;
    private Boolean saveLogin;
    public  boolean isPasswordSameAsParse;
    public String passwordFromParse;

    private CallbackManager callbackManager;
    private TextView info;
    private LoginButton loginButton;


    private String[] fbUserFullName;
    private String fbUserName;
    private String fbUserLastName;
    private String fbUserEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        //Facebook
        callbackManager = CallbackManager.Factory.create();
        boolean loggedIn = AccessToken.getCurrentAccessToken() != null;
        if(loggedIn) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        info = (TextView)findViewById(R.id.info);
        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
              /*  info.setText("User ID:  " +
                        loginResult.getAccessToken().getUserId() + "\n" +
                        "Auth Token: " + loginResult.getAccessToken().getToken());*/
                String logtoken = loginResult.getAccessToken().getToken();

                //Add parse.com
                //...
                GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject me, GraphResponse response) {

                                if (response.getError() != null) {
                                    // handle error
                                } else {
                                    Profile profile = Profile.getCurrentProfile();
                                    fbUserEmail = me.optString("id");
                                    fbUserFullName = me.optString("name").split("\\s+");
                                    fbUserName = fbUserFullName[0];
                                    fbUserLastName = fbUserFullName[1];
                                    //fbUserName = profile.getFirstName();
                                    //fbUserLastName = profile.getLastName();
                                    ParseObject testAccount = new ParseObject("TestAccount");
                                    testAccount.put("Email", fbUserEmail);
                                    testAccount.put("Password", fbUserEmail);
                                    testAccount.put("Name", fbUserName);
                                    testAccount.put("Lastname", fbUserLastName);
                                    testAccount.saveInBackground();
                                    writeToFileUserInformation(fbUserEmail, fbUserEmail);
                                }
                            }
                        }).executeAsync();



                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);



     /*           Bundle params = new Bundle();
               // params.putString("type", "event");
               // params.putString("q", "sarıyer");
              //  params.putString("fields", "email");
                params.putString("fields", "first_name, last_name, email");
                //params.putString("fields", "last_name");
            // make the API call
                new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/me",
                        params,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
            // handle the result
                                info.setText(response.toString());
                            }
                        }
                ).executeAsync();*/
            }

            @Override
            public void onCancel() {
                info.setText("Login attempt cancelled.");
            }

            @Override
            public void onError(FacebookException e) {
                info.setText("Login attempt failed.");
            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void runQuery(final String usernameET, final String passwordET) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("TestAccount");
        query.whereEqualTo("Email", usernameET);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    Log.d("score", "The getFirst request failed.");
                    afterQueryProcessing("", "");
                    // You don't have a good value to use, so figure
                    // out a way to handle that scenario
                } else {
                    Log.d("id", "Retrieved the object.");
                    String status = object.getString("Password");
                    passwordFromParse = object.getString("Password");
                    System.out.println("Hospital: " + status);
                    System.out.println(status + passwordFromParse);
                    // You have a good value to use, so
                    // now you can actually use it
                    afterQueryProcessing(usernameET, passwordET);
                }
            }
        });
    }

    private void afterQueryProcessing(String usernameET, String passwordET) {
        // You can access m2Status here reliably,
        // assuming you only call this method
        // as shown above, but you should still
        // use defensive programming
        if(passwordET.equals(passwordFromParse)){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("Username", usernameET);
            intent.putExtra("Password", passwordET);
            startActivity(intent);
        } else {
            new AlertDialog.Builder(this).setTitle("Warning").setMessage("Incorrect Email or Password!").setNeutralButton("Close", null).show();
        }

    }

    private void writeToFileUserInformation(final String usernameInformation, final String passwordInformation) {

        //Write to file for saving username data
        FileOutputStream fosUsername = null;
        try {
            fosUsername = openFileOutput("UsernameInfromation.txt", MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("usernameInformation........." + usernameInformation + "............");
            fosUsername.write(usernameInformation.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fosUsername.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Write to file for saving password data
        FileOutputStream fosPassword = null;
        try {
            fosPassword = openFileOutput("PasswordInfromation.txt", MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("passwordInformation........." + passwordInformation + "............");
            fosPassword.write(passwordInformation.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fosPassword.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Write to file for saving objectid
        ParseQuery<ParseObject> query = ParseQuery.getQuery("TestAccount");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> parseAccounts, ParseException e) {
                if (e == null) {
                    int len = parseAccounts.size();
                    for (int i = 0; i < len; i++) {
                        //get the i th element of Accounts in order to obtain details
                        ParseObject p = parseAccounts.get(i);

                        //get the detail information of userAccount
                        String pEmail = p.getString("Email");
                        String pPassword = p.getString("Password");
                        String pObjectId;

                        if(pEmail.equals(usernameInformation) && pPassword.equals(passwordInformation)){
                            pObjectId = p.getObjectId();
                            System.out.println("pObjectId........." + pObjectId + "............" + pObjectId);
                            FileOutputStream fosObjectid = null;
                            try {
                                fosObjectid = openFileOutput("ObjectidInfromation.txt", MODE_PRIVATE);
                            } catch (FileNotFoundException ex) {
                                ex.printStackTrace();
                            }
                            try {
                                System.out.println("ObjectidInformation........." + pObjectId + "............");
                                fosObjectid.write(pObjectId.getBytes());
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            try {
                                fosObjectid.close();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            return;
                        }

                    }
                } else {
                    System.out.println("Error::: in writeToFileUserInformation()-->Parse!");

                }
            }
        });




    }

    public void onLogin(View view) {

        final String usernameET = String.valueOf(etUsername.getText());
        final String passwordET = String.valueOf(etPassword.getText());
        isPasswordSameAsParse = false;

        if((usernameET== null || usernameET.isEmpty())|| (passwordET== null || passwordET.isEmpty()) )
        {
            new AlertDialog.Builder(this).setTitle("Warning").setMessage("Please enter valid username and password!").setNeutralButton("Close", null).show();
        } else {
            writeToFileUserInformation(usernameET, passwordET);
            runQuery(usernameET, passwordET);
        }
    }

    public void onSignup (View view) {
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }

}

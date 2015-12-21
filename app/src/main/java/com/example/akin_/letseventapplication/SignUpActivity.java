package com.example.akin_.letseventapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

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
        String SpinnerSex = spinnersex.getSelectedItem().toString();

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
        } else if (SpinnerSex.equals(spinnersex.getItemAtPosition(0))) {
            new AlertDialog.Builder(this).setTitle("Warning").setMessage("Please select a gender!").setNeutralButton("Close", null).show();
        } else {
            //if every field is okey, then
            //save to the Parse
            ParseObject testAccount = new ParseObject("TestAccount");
            testAccount.put("Email", emailET);
            testAccount.put("Password", passwordET);
            testAccount.put("Name", nameET);
            testAccount.put("Lastname", lastnameET);
            testAccount.put("Sex", SpinnerSex);
            testAccount.saveInBackground();

            writeToFileUserInformation(emailET, passwordET);

            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
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

}

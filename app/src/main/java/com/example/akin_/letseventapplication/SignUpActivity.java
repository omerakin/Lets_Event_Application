package com.example.akin_.letseventapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.parse.Parse;
import com.parse.ParseObject;

public class SignUpActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText confirmpassword;
    private EditText name;
    private EditText surname;
    private EditText phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confirmpassword = (EditText) findViewById(R.id.confirmpassword);
        name = (EditText) findViewById(R.id.name);
        surname = (EditText) findViewById(R.id.surname);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);

    }

    public void onRealSignup (View view) {

        // getting Et as a string
        String emailET = String.valueOf(email.getText());
        String passwordET = String.valueOf(password.getText());
        String confirmpasswordET = String.valueOf(confirmpassword.getText());
        String nameET = String.valueOf(name.getText());
        String surnameET = String.valueOf(surname.getText());
        String phoneNumberET = String.valueOf(phoneNumber.getText());


        //save to the Parse
        ParseObject testAccount = new ParseObject("TestAccount");
        testAccount.put("Email", emailET);
        testAccount.put("Password", passwordET);
        testAccount.put("Name", nameET);
        testAccount.put("Surname", surnameET);
        testAccount.put("Phone_number", phoneNumberET);
        testAccount.saveInBackground();

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }
}

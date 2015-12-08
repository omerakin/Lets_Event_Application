package com.example.akin_.letseventapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.parse.ParseObject;

public class SignUpActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText confirmpassword;
    private EditText name;
    private EditText lastname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confirmpassword = (EditText) findViewById(R.id.confirmpassword);
        name = (EditText) findViewById(R.id.name);
        lastname = (EditText) findViewById(R.id.lastname);
    }

    public void onRealSignup (View view) {

        // getting Et as a string
        String emailET = String.valueOf(email.getText());
        String passwordET = String.valueOf(password.getText());
        String confirmpasswordET = String.valueOf(confirmpassword.getText());
        String nameET = String.valueOf(name.getText());
        String lastnameET = String.valueOf(lastname.getText());

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
        } else {
            //if every field is okey, then
            //save to the Parse
            ParseObject testAccount = new ParseObject("TestAccount");
            testAccount.put("Email", emailET);
            testAccount.put("Password", passwordET);
            testAccount.put("Name", nameET);
            testAccount.put("Lastname", lastnameET);
            testAccount.saveInBackground();

            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }




    }
}

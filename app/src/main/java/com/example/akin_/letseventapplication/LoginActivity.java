package com.example.akin_.letseventapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private CheckBox checkBox;
    private SharedPreferences login;
    private SharedPreferences.Editor loginEditor;
    private Boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        String usernameET = String.valueOf(etUsername.getText());
        String passwordET = String.valueOf(etPassword.getText());

        if((usernameET== null || usernameET.isEmpty())|| (passwordET== null || passwordET.isEmpty()) )
        {
            new AlertDialog.Builder(this).setTitle("Warning").setMessage("Please enter valid username and password!").setNeutralButton("Close", null).show();
        } else {

            if(checkBox.isChecked()) {
                loginEditor.putBoolean("saveLogin", true);
                loginEditor.putString("username", usernameET);
                loginEditor.putString("password", passwordET);
                loginEditor.commit();
            } else {
                loginEditor.clear();
                loginEditor.commit();
            }

            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("Username", usernameET);
            intent.putExtra("Password", passwordET);
            startActivity(intent);

        }



    }
}

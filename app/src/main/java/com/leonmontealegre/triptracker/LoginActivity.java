package com.leonmontealegre.triptracker;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends ActionBarActivity {

    private static final String TAG = "LoginActivity";

    private EditText userNameField;
    private EditText passwordField;

    private Button loginButton;

    private TextView signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameField = (EditText)findViewById(R.id.username_input_field);
        passwordField = (EditText)findViewById(R.id.password_input_field);

        loginButton = (Button)findViewById(R.id.login_button);

        signUpButton = (TextView)findViewById(R.id.sign_up_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin(userNameField.getText().toString(), passwordField.getText().toString());
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });
    }

    private void attemptLogin(String username, String password) {
        Log.d(TAG, "Attempted login with username : " + username + ", and password : " + password);
        // check if it works
        // start intent to load main activity

    }

}
package com.leonmontealegre.triptracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessFault;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private EditText userNameField;
    private EditText passwordField;

    private Button loginButton;

    private TextView signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Backendless.initApp(this, Options.APP_ID, Options.SECRET_KEY, Options.VERSION);

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
        Toast.makeText(this, "Logging in...", Toast.LENGTH_SHORT).show();
        Backendless.UserService.login(username, Encoder.encodePassword(password), new BackendlessCallback<BackendlessUser>(){
            @Override
            public void handleResponse(BackendlessUser backendlessUser) {
                Intent i = new Intent(LoginActivity.this, TripListActivity.class);
                startActivity(i);
            }
            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(LoginActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
package com.leonmontealegre.triptracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private EditText userNameField;
    private EditText passwordField;

    private Button loginButton;

    private TextView signUpButton;
    private TextView forgotPasswordButton;

    private CheckBox rememberUsernameCheckBox;
    private CheckBox rememberPasswordCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        Backendless.initApp(this, Options.APP_ID, Options.SECRET_KEY, Options.VERSION);

        userNameField = (EditText)findViewById(R.id.username_input_field);
        passwordField = (EditText)findViewById(R.id.password_input_field);

        loginButton = (Button)findViewById(R.id.login_button);

        signUpButton = (TextView)findViewById(R.id.sign_up_button);
        forgotPasswordButton = (TextView)findViewById(R.id.forgot_password_button);

        rememberUsernameCheckBox = (CheckBox)findViewById(R.id.remember_username_check_box);
        rememberPasswordCheckBox = (CheckBox)findViewById(R.id.remember_password_check_box);

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
        Backendless.UserService.login(username, Encoder.encodePassword(password), new LoadingCallback<BackendlessUser>(this, "Logging in...") {
            @Override
            public void handleResponse(BackendlessUser backendlessUser) {
                super.handleResponse(backendlessUser);
                User.login(backendlessUser);
                Intent i = new Intent(LoginActivity.this, TripListActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        passwordField.setText("");
    }

}
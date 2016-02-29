package com.leonmontealegre.triptracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class EditTripActivity  extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

}
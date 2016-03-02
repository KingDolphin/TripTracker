package com.leonmontealegre.triptracker;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class EditTripActivity extends AppCompatActivity {

    private static final String TAG = "EditTripActivity";

    private Button startDateButton;
    private Button endDateButton;

    private DialogFragment datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        datePicker = new DatePickerFragment();

        startDateButton = (Button)findViewById(R.id.start_date_button);
        endDateButton = (Button)findViewById(R.id.end_date_button);

        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragManager = getSupportFragmentManager();
                datePicker.show(fragManager, "datePicker");
            }
        });

        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragManager = getSupportFragmentManager();
                datePicker.show(fragManager, "datePicker");
            }
        });
    }

}
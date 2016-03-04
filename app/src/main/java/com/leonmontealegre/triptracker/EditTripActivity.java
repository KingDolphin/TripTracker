package com.leonmontealegre.triptracker;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Calendar;
import java.util.Date;

public class EditTripActivity extends AppCompatActivity {

    private static final String TAG = "EditTripActivity";

    private EditText tripNameField;
    private EditText tripDescField;

    private Button startDateButton;
    private Button endDateButton;
    private ImageButton saveTripButton;
    private ImageButton cancelTripButton;

    private CheckBox isPublicCheckbox;

    private DatePickerFragment datePicker;

    private boolean settingStartDate = false;
    private boolean settingEndDate = false;

    private Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip);

        tripNameField = (EditText)findViewById(R.id.trip_name_input_field);
        tripDescField = (EditText)findViewById(R.id.trip_desc_input_field);

        startDateButton = (Button)findViewById(R.id.start_date_button);
        endDateButton = (Button)findViewById(R.id.end_date_button);
        saveTripButton = (ImageButton)findViewById(R.id.save_trip_button);
        cancelTripButton = (ImageButton)findViewById(R.id.cancel_trip_button);

        isPublicCheckbox = (CheckBox)findViewById(R.id.is_public_checkbox);

        trip = (Trip)getIntent().getSerializableExtra(Trip.TRIP_EXTRA);

        tripNameField.setText(trip.getName());
        tripDescField.setText(trip.getDescription());

        Calendar cal = Calendar.getInstance();
        cal.setTime(trip.getStartDate());
        startDateButton.setText(cal.get(Calendar.MONTH)+1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR));
        cal.setTime(trip.getEndDate());
        endDateButton.setText(cal.get(Calendar.MONTH)+1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR));

        isPublicCheckbox.setChecked(trip.isPublic());

        saveTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trip.setName(tripNameField.getText().toString());
                trip.setDescription(tripDescField.getText().toString());
                trip.setPublic(isPublicCheckbox.isChecked());
                Intent data = new Intent();
                data.putExtra("TRIP", trip);
                if (getParent() == null)
                    setResult(Activity.RESULT_OK, data);
                else
                    getParent().setResult(Activity.RESULT_OK, data);
                finish();
            }
        });
        cancelTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        datePicker = new DatePickerFragment();

        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, day);
                if (settingStartDate) {
                    trip.setStartDate(cal.getTime());
                    startDateButton.setText(month + "/" + day + "/" + year);
                } else if (settingEndDate) {
                    trip.setEndDate(cal.getTime());
                    endDateButton.setText(month + "/" + day + "/" + year);
                }
                settingStartDate = settingEndDate = false;
            }
        });
        datePicker.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                settingStartDate = settingEndDate = false;
            }
        });

        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingStartDate = true;
                datePicker.setDate(trip.getStartDate());
                FragmentManager fragManager = getSupportFragmentManager();
                datePicker.show(fragManager, "datePicker");
            }
        });

        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingEndDate = true;
                datePicker.setDate(trip.getEndDate());
                FragmentManager fragManager = getSupportFragmentManager();
                datePicker.show(fragManager, "datePicker");
            }
        });
    }

}
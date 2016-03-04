package com.leonmontealegre.triptracker;

import android.content.DialogInterface;
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

        String tripName = getIntent().getStringExtra(Trip.NAME_EXTRA);
        String description = getIntent().getStringExtra(Trip.DESC_EXTRA);
        String creatorName = getIntent().getStringExtra(Trip.CREATOR_EXTRA);
        Date startDate = (Date)getIntent().getSerializableExtra(Trip.START_DATE_EXTRA);
        Date endDate = (Date)getIntent().getSerializableExtra(Trip.END_DATE_EXTRA);
        boolean isPublic = getIntent().getBooleanExtra(Trip.IS_PUBLIC_EXTRA, false);

        trip = new Trip(tripName, creatorName, description, startDate, endDate, isPublic);

        tripNameField.setText(tripName);
        tripDescField.setText(description);

        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        startDateButton.setText(cal.get(Calendar.MONTH)+1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR));
        cal.setTime(endDate);
        endDateButton.setText(cal.get(Calendar.MONTH)+1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR));

        isPublicCheckbox.setChecked(isPublic);

        saveTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trip.name = tripNameField.getText().toString();
                trip.description = tripDescField.getText().toString();
                trip.isPublic = isPublicCheckbox.isChecked();
                getIntent().putExtra("test", true);
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
                    trip.startDate = cal.getTime();
                    startDateButton.setText(month + "/" + day + "/" + year);
                } else if (settingEndDate) {
                    trip.endDate = cal.getTime();
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
                datePicker.setDate(trip.startDate);
                FragmentManager fragManager = getSupportFragmentManager();
                datePicker.show(fragManager, "datePicker");
            }
        });

        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingEndDate = true;
                datePicker.setDate(trip.endDate);
                FragmentManager fragManager = getSupportFragmentManager();
                datePicker.show(fragManager, "datePicker");
            }
        });
    }

}
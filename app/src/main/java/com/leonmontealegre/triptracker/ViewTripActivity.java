package com.leonmontealegre.triptracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Calendar;

public class ViewTripActivity  extends AppCompatActivity {

    private static final String TAG = "EditTripActivity";

    private TextView tripNameText;
    private TextView tripDescText;

    private TextView startDateText;
    private TextView endDateText;

    private Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trip);

        tripNameText = (TextView)findViewById(R.id.trip_name_text);
        tripDescText = (TextView)findViewById(R.id.trip_desc_text);

        startDateText = (TextView)findViewById(R.id.start_date_text);
        endDateText = (TextView)findViewById(R.id.end_date_text);

        trip = (Trip)getIntent().getSerializableExtra(Trip.TRIP_EXTRA);

        tripNameText.setText(trip.getName());
        tripDescText.setText(trip.getDescription());

        tripNameText.setSelected(true);

        Calendar cal = Calendar.getInstance();
        cal.setTime(trip.getStartDate());
        startDateText.setText(cal.get(Calendar.MONTH)+1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR));
        cal.setTime(trip.getEndDate());
        endDateText.setText(cal.get(Calendar.MONTH)+1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR));
    }

}

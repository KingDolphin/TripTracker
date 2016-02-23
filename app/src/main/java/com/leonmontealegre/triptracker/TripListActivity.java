package com.leonmontealegre.triptracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class TripListActivity extends AppCompatActivity {

    private static final String TAG = "TripListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);

        TripListFragment tripListFragment = new TripListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.tripListContainer, tripListFragment).commit();
    }

}

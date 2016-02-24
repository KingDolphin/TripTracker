package com.leonmontealegre.triptracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;

public class TripListActivity extends AppCompatActivity {

    private static final String TAG = "TripListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);

        TripListFragment tripListFragment = new TripListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.tripListContainer, tripListFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dropdown_trip_list_action_bar, menu);

        return super.onCreateOptionsMenu(menu);
    }

}

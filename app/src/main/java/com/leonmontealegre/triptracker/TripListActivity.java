package com.leonmontealegre.triptracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_help:
                Log.d(TAG, "Selected help");
                return true;
            case R.id.menu_item_settings:
                Log.d(TAG, "Selected settings");
                return true;
            case R.id.menu_item_logout:
                User.logout();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

package com.leonmontealegre.triptracker;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.persistence.BackendlessDataQuery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TripListFragment extends ListFragment {

    public static final String TAG = "TripListFragment";

    public static final int EDIT_TRIP_REQUEST_CODE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        final View v = super.onCreateView(inflater, parent, savedInstanceState);

        refreshTrips();
        ListView listView = (ListView)v.findViewById(android.R.id.list);
        registerForContextMenu(listView);

        FloatingActionButton fab = (FloatingActionButton)getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTrip(new Trip());
            }
        });

        return v;
    }

    private void refreshTrips() {
        final List list = new ArrayList();
        BackendlessDataQuery query = new BackendlessDataQuery();
        query.setWhereClause("ownerId = '"+User.getUserID()+"'");
        Backendless.Data.of(Trip.class).find(query, new LoadingCallback<BackendlessCollection<Trip>>(this.getActivity(), "Loading...") {
            @Override
            public void handleResponse(BackendlessCollection<Trip> trips) {
                super.handleResponse(trips);
                List myTrips = trips.getCurrentPage();
                list.add(getResources().getText(R.string.trip_list_my_trips).toString());
                list.addAll(myTrips);
                list.add(getResources().getText(R.string.trip_list_all_trips).toString());

                BackendlessDataQuery query2 = new BackendlessDataQuery();
                query2.setWhereClause("public = True AND ownerId != '"+User.getUserID()+"'");
                Backendless.Data.of(Trip.class).find(query2, new LoadingCallback<BackendlessCollection<Trip>>(getActivity(), "Loading...") {
                    @Override
                    public void handleResponse(BackendlessCollection<Trip> trips) {
                        super.handleResponse(trips);
                        list.addAll(trips.getCurrentPage());

                        TripListAdapter adapter = new TripListAdapter(list);
                        setListAdapter(adapter);
                        setHasOptionsMenu(true);
                    }
                });
            }
        });
    }

    public void saveTrip(Trip trip) {
        Backendless.Data.of(Trip.class).save(trip, new LoadingCallback<Trip>(getActivity(), "Creating...") {
            @Override
            public void handleResponse(Trip response) {
                super.handleResponse(response);
                Toast.makeText(getActivity(), "Successfully saved!", Toast.LENGTH_SHORT).show();
                refreshTrips();
            }
        });
    }

    public void removeTrip(Trip trip) {
        Backendless.Data.of(Trip.class).remove(trip, new LoadingCallback<Long>(getActivity(), "Removing...") {
            @Override
            public void handleResponse(Long response) {
                super.handleResponse(response);
                Toast.makeText(getActivity(), "Removed!", Toast.LENGTH_SHORT).show();
                refreshTrips();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_CANCELED)
            return;

        if (requestCode == EDIT_TRIP_REQUEST_CODE) {
            Trip trip = (Trip)data.getSerializableExtra(Trip.TRIP_EXTRA);
            saveTrip(trip);
        }
    }

    private void deleteTrip(final Trip trip) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this.getActivity());
        alert.setIcon(R.drawable.ic_warning_black_24dp);
        alert.setTitle("Deleting Trip");
        alert.setMessage("Are you sure you want to delete this trip?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                removeTrip(trip);
            }
        });
        alert.setNegativeButton("No", null);
        alert.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshTrips();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Object item = getListAdapter().getItem(position);
        Log.d(TAG, "onListItemClick: " + position);
        if (item instanceof Trip) {
            Trip trip = (Trip)item;

            Intent i = new Intent(this.getActivity(), ViewTripActivity.class);
            i.putExtra(Trip.TRIP_EXTRA, trip);
            startActivity(i);
        }
    }

    private class TripListAdapter extends ArrayAdapter {
        public TripListAdapter(List trips) {
            super(getActivity(), 0, trips);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Object item = getItem(position);

            if (item instanceof String) {       // Separator
                final String title = (String)item;

                if (convertView == null)
                    convertView = getActivity().getLayoutInflater().inflate(R.layout.list_separator, null);

                TextView separatorText = (TextView) convertView.findViewById(R.id.separator_text);
                separatorText.setText(title);
            } else if (item instanceof Trip) {  // Trip
                final Trip trip = (Trip)item;

                if (convertView == null)
                    convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_trip, null);

                TextView nameTextView = (TextView) convertView.findViewById(R.id.trip_name_text);
                nameTextView.setText(trip.getName());
                nameTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(TripListFragment.this.getActivity(), ViewTripActivity.class);
                        i.putExtra(Trip.TRIP_EXTRA, trip);
                        startActivity(i);
                    }
                });
                nameTextView.setSelected(true);

                TextView creatorTextView = (TextView) convertView.findViewById(R.id.trip_creator_text);
                String defaultText = getResources().getText(R.string.default_trip_creator_text).toString();
                Calendar cal = Calendar.getInstance();
                cal.setTime(trip.getStartDate());
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH) + 1;
                int day = cal.get(Calendar.DAY_OF_MONTH);
                creatorTextView.setText(defaultText.replace("{USER}", trip.getCreatorName()).replace("{DATE}", month + "/" + day + "/" + year));

                final ImageButton editButton = (ImageButton) convertView.findViewById(R.id.trip_edit_button);
                if (User.getUsername().equals(trip.getCreatorName())) {
                    editButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PopupMenu popupMenu = new PopupMenu(TripListFragment.this.getActivity(), editButton);
                            popupMenu.getMenuInflater().inflate(R.menu.menu_popup_edit_trip, popupMenu.getMenu());
                            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                public boolean onMenuItemClick(MenuItem item) {
                                    switch (item.getItemId()) {
                                        case R.id.menu_item_edit_trip:
                                            Intent i = new Intent(TripListFragment.this.getActivity(), EditTripActivity.class);
                                            i.putExtra(Trip.TRIP_EXTRA, trip);
                                            startActivityForResult(i, EDIT_TRIP_REQUEST_CODE);
                                            return true;
                                        case R.id.menu_item_delete_trip:
                                            deleteTrip(trip);
                                            return true;
                                        default:
                                            return false;
                                    }
                                }
                            });
                            popupMenu.show();
                        }
                    });
                } else {
                    editButton.setVisibility(View.GONE);
                }
            }
            return convertView;
        }
    }

}

package com.leonmontealegre.triptracker;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
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

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TripListFragment extends ListFragment {

    public static final String TAG = "TripListFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        final View v = super.onCreateView(inflater, parent, savedInstanceState);

        Backendless.Data.of(Trip.class).find(new AsyncCallback<BackendlessCollection<Trip>>() {
            @Override
            public void handleResponse(BackendlessCollection<Trip> trips) {
                TripListAdapter adapter = new TripListAdapter(trips.getCurrentPage());
                setListAdapter(adapter);
                setHasOptionsMenu(true);

                ListView listView = (ListView)v.findViewById(android.R.id.list);
                registerForContextMenu(listView);
            }
            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                System.out.println("Server reported an error - " + backendlessFault.getMessage());
            }
        });

        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
//        FamilyMember f = ((FamilyMemberAdapter)getListAdapter()).getItem(position);
//        Log.d(TAG, f.toString() + " was clicked. " + FamilyMemberActivity.class + " AT POSITION : " + position);
//        Intent i = new Intent(getActivity(), FamilyMemberActivity.class);
//        Log.d(TAG, "RELATION : " + f.getRelation() + ", " + position);
//        i.putExtra(FamilyMember.RELATION_KEY, f.getRelation());
//        i.putExtra(FamilyMember.INDEX_KEY, position);
//        startActivity(i);
        Log.d(TAG, "List item at index " + position + " was clicked");
    }

    private class TripListAdapter extends ArrayAdapter<Trip> {
        public TripListAdapter(List trips) {
            super(getActivity(), 0, trips);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_trip, null);

            Trip trip = getItem(position);

            TextView nameTextView = (TextView)convertView.findViewById(R.id.trip_name_text);
            nameTextView.setText(trip.name);

            TextView creatorTextView = (TextView)convertView.findViewById(R.id.trip_creator_text);
            String defaultText = getResources().getText(R.string.default_trip_creator_text).toString();
            creatorTextView.setText(defaultText.replace("{USER}", trip.creatorName).replace("{DATE}", trip.startDate.toString()));

            final ImageButton editButton = (ImageButton)convertView.findViewById(R.id.trip_edit_button);
            if (User.getUsername().equals(trip.creatorName)) {
                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popupMenu = new PopupMenu(TripListFragment.this.getActivity(), editButton);
                        popupMenu.getMenuInflater().inflate(R.menu.menu_popup_edit_trip, popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.menu_item_edit_trip:
                                        Log.d(TAG, "Selected edit trip");
                                        return true;
                                    case R.id.menu_item_delete_trip:
                                        Log.d(TAG, "Selected delete trip");
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

            return convertView;
        }
    }

}

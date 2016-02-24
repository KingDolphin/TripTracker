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

import java.util.ArrayList;
import java.util.List;

public class TripListFragment extends ListFragment {

    public static final String TAG = "TripListFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);

        ArrayList<Trip> trips = new ArrayList<>();


        TripListAdapter adapter = new TripListAdapter(trips);
        setListAdapter(adapter);
        setHasOptionsMenu(true);

        ListView listView = (ListView)v.findViewById(android.R.id.list);
        registerForContextMenu(listView);

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
            nameTextView.setText(trip.getName());

            TextView creatorTextView = (TextView)convertView.findViewById(R.id.trip_creator_text);
            String defaultText = getResources().getText(R.string.default_trip_creator_text).toString();
            creatorTextView.setText(defaultText.replace("{USER}", trip.getCreator()).replace("{DATE}", trip.getStartDate()));

            final ImageButton editButton = (ImageButton)convertView.findViewById(R.id.trip_edit_button);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Edited trip at index " + position);
                    PopupMenu popupMenu = new PopupMenu(TripListFragment.this.getActivity(), editButton);
                    popupMenu.getMenuInflater().inflate(R.menu.menu_popup_edit_trip, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            Log.d(TAG, "Item : " + item.getTitle() + " clicked on trip at position : " + position);
                            return true;
                        }
                    });
                    popupMenu.show();
                }
            });

            return convertView;
        }
    }

}

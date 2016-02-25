package com.leonmontealegre.triptracker;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class User {

    public static final String NAME_KEY = "name";
    public static final String USERNAME_KEY = "username";
    public static final String TRIPS_KEY = "trips";

    private static User currentUser = null;

    public final String name;
    public final String username;
    public final String email;
    private BackendlessUser user;

    private User(BackendlessUser user, String name, String username, String email) {
        this.user = user;
        this.name = name;
        this.username = username;
        this.email = email;
    }

    public static void login(BackendlessUser backendlessUser) {
        if (currentUser != null)
            logout();
        currentUser = new User(backendlessUser, backendlessUser.getProperty(NAME_KEY).toString(), backendlessUser.getProperty(USERNAME_KEY).toString(), backendlessUser.getEmail());
    }

    public static void logout() {
        currentUser = null;
    }

    public static void addTrip(Trip trip) {
        Backendless.Data.of(Trip.class).save(trip, new AsyncCallback<Trip>() {
            @Override
            public void handleResponse(Trip response) {
                System.out.println("saved trip success");
            }
            @Override
            public void handleFault(BackendlessFault fault) {
                System.out.println("saved trip failed : " + fault.getMessage());
            }
        });
    }

    public static String getName() {
        if (currentUser != null)
            return currentUser.name;
        return "";
    }

    public static String getUsername() {
        if (currentUser != null)
            return currentUser.username;
        return "";
    }

    public static String getEmail() {
        if (currentUser != null)
            return currentUser.email;
        return "";
    }

}
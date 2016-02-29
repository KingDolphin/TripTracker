package com.leonmontealegre.triptracker;

import com.backendless.BackendlessUser;

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

    public static String getUserID() {
        return currentUser.user.getUserId();
    }

}
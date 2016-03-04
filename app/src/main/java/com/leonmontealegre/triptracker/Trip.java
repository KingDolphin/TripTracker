package com.leonmontealegre.triptracker;

import java.util.Date;

public class Trip {

    public static final String NAME_EXTRA = "TRIP_NAME";
    public static final String CREATOR_EXTRA = "TRIP_CREATOR_NAME";
    public static final String DESC_EXTRA = "TRIP_DESCRIPTION";
    public static final String START_DATE_EXTRA = "TRIP_START_DATE";
    public static final String END_DATE_EXTRA = "TRIP_END_DATE";
    public static final String IS_PUBLIC_EXTRA = "TRIP_IS_PUBLIC";

    public String name;
    public String creatorName;
    public String description;
    public Date startDate;
    public Date endDate;
    public boolean isPublic;

    public Trip(String name, String creatorName, String description, Date startDate, Date endDate, boolean isPublic) {
        this.name = name;
        this.creatorName = creatorName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isPublic = isPublic;
    }

    public Trip() {
        this("New Trip", User.getUsername(), "A new trip.", new Date(), new Date(), false);
    }

    @Override
    public String toString() {
        return name + ", " + description;
    }

}

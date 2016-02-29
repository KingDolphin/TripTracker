package com.leonmontealegre.triptracker;

import java.util.Date;

public class Trip {

    public String name;
    public String creatorName;
    public String description;
    public Date startDate;
    public Date endDate;

    public Trip(String name, String creatorName, String description, Date startDate, Date endDate) {
        this.name = name;
        this.creatorName = creatorName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Trip() {
        this("New Trip", User.getUsername(), "A new trip.", new Date(), new Date());
    }

    @Override
    public String toString() {
        return name + ", " + description;
    }

}

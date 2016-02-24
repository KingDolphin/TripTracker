package com.leonmontealegre.triptracker;

import java.util.Date;

public class Trip {

    private String name;
    private final String creatorName;
    private String description;
    private Date startDate;
    private Date endDate;

    public Trip(String name, String creatorName, String description, Date startDate, Date endDate) {
        this.name = name;
        this.creatorName = creatorName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public void setStartDate(Date date) {
        this.startDate = date;
    }

    public void setEndDate(Date date) {
        this.endDate = date;
    }

    public String getName() {
        return this.name;
    }

    public String getCreator() {
        return this.creatorName;
    }

    public String getDescription() { return this.description; }

    public String getStartDate() {
        return this.startDate.toString();
    }

    public String getEndDate() { return this.endDate.toString(); }

}

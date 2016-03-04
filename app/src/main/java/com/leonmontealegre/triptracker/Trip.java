package com.leonmontealegre.triptracker;

import java.io.Serializable;
import java.util.Date;

public class Trip implements Serializable {

    private static final long serialVersionUID = -39746785349320499L;

    public static final transient String TRIP_EXTRA = "TRIP";

    private String name;
    private String creatorName;
    private String description;
    private Date startDate;
    private Date endDate;
    private boolean isPublic;
    private String objectId;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getObjectId() {
        return objectId;
    }

    @Override
    public String toString() {
        return name + ", " + description;
    }

}

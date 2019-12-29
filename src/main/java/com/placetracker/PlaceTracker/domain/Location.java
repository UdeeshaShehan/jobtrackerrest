package com.placetracker.PlaceTracker.domain;

import java.io.Serializable;
import java.util.Date;

public class Location implements Serializable {

    private double longitude;
    private double latitude;
    private Date updateTime;

    public Location() {
    }

    public Location(double longitude, double latitude, Date updateTime) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.updateTime = updateTime;
    }
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

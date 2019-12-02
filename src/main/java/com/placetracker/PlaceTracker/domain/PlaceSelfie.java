package com.placetracker.PlaceTracker.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Document("job")
public class PlaceSelfie implements Serializable {

    @Id
    public String id;
    private String firstSelfie;
    private String lastSelfie;
    /*private double latitude1;
    private double longitude1;
    private double latitude2;
    private double longitude2;*/
    private Location firstLocation;
    private Location lastLocation;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date firstSelfieDate;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date lastSelfieDate;
    private String jobName;
    private int isJobAdded;
    private String jobDescription;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date dateOfJob;
    private String userName;
    private String email;
    private List<Location> locations;


    public PlaceSelfie() {
    }

    public PlaceSelfie(String _id, String firstSelfie, String lastSelfie, Location firstLocation, Location lastLocation,
                       Date firstSelfieDate,
                       Date lastSelfieDate, String jobName, int isJobAdded, String jobDescription,
                       Date dateOfJob, String userName, String email) {
        this.id = _id;
        this.firstSelfie = firstSelfie;
        this.lastSelfie = lastSelfie;
        this.firstSelfieDate = firstSelfieDate;
        this.lastSelfieDate = lastSelfieDate;
        this.jobName = jobName;
        this.isJobAdded = isJobAdded;
        this.jobDescription = jobDescription;
        this.dateOfJob = dateOfJob;
        this.userName = userName;
        this.email = email;
        this.firstLocation = firstLocation;
        this.lastLocation = lastLocation;
    }

    public PlaceSelfie(String firstSelfie, String lastSelfie, Location firstLocation, Location lastLocation,
                       Date firstSelfieDate, Date lastSelfieDate, String jobName, int isJobAdded, String jobDescription,
                        Date dateOfJob,  String userName, String email) {
        this.firstSelfie = firstSelfie;
        this.lastSelfie = lastSelfie;
        this.firstLocation = firstLocation;
        this.lastLocation = lastLocation;
        this.firstSelfieDate = firstSelfieDate;
        this.lastSelfieDate = lastSelfieDate;
        this.jobName = jobName;
        this.isJobAdded = isJobAdded;
        this.jobDescription = jobDescription;
        this.dateOfJob = dateOfJob;
        this.userName = userName;
        this.email = email;
    }

    public PlaceSelfie(String id) {
        this.id = id;
    }

    public String getFirstSelfie() {
        return firstSelfie;
    }

    public void setFirstSelfie(String firstSelfie) {
        this.firstSelfie = firstSelfie;
    }

    public String getLastSelfie() {
        return lastSelfie;
    }

    public void setLastSelfie(String lastSelfie) {
        this.lastSelfie = lastSelfie;
    }

    public Location getFirstLocation() {
        return firstLocation;
    }

    public void setFirstLocation(Location firstLocation) {
        this.firstLocation = firstLocation;
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }

    public List<Location> getLocations() {
        locations = Optional.ofNullable(locations).orElse(new ArrayList<>());
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public Date getFirstSelfieDate() {
        return firstSelfieDate;
    }

    public void setFirstSelfieDate(Date firstSelfieDate) {
        this.firstSelfieDate = firstSelfieDate;
    }

    public Date getLastSelfieDate() {
        return lastSelfieDate;
    }

    public void setLastSelfieDate(Date lastSelfieDate) {
        this.lastSelfieDate = lastSelfieDate;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public int getIsJobAdded() {
        return isJobAdded;
    }

    public void setIsJobAdded(int isJobAdded) {
        this.isJobAdded = isJobAdded;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public Date getDateOfJob() {
        return dateOfJob;
    }

    public void setDateOfJob(Date dateOfJob) {
        this.dateOfJob = dateOfJob;
    }

    public void addLocation(Location location) {
        locations.add(location);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package com.placetracker.PlaceTracker.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "users")
public class User {

	@Id
	private String id;
	@Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
	private String email;

	private String address;
	private String mobileNumber;
	private String password;
	private String fullname;
	private String mentorMobileNumber;
	private String organization;
	private boolean enabled;
	//@DBRef
	private String role;
	public User() {
	}

	public User(String email, String address, String mobileNumber, String fullname, String mentorMobileNumber, String organization) {
		this.email = email;
		this.address = address;
		this.mobileNumber = mobileNumber;
		this.fullname = fullname;
		this.mentorMobileNumber = mentorMobileNumber;
		this.organization = organization;
	}

	public User(String email, String address, String mobileNumber, String fullname, String mentorMobileNumber) {
		this.email = email;
		this.address = address;
		this.mobileNumber = mobileNumber;
		this.fullname = fullname;
		this.mentorMobileNumber = mentorMobileNumber;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getMentorMobileNumber() {
		return mentorMobileNumber;
	}

	public void setMentorMobileNumber(String mentorMobileNumber) {
		this.mentorMobileNumber = mentorMobileNumber;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}
}

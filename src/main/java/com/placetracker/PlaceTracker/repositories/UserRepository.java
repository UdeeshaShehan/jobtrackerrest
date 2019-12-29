package com.placetracker.PlaceTracker.repositories;


import com.placetracker.PlaceTracker.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

	User findByEmail(String email);
	User findByMobileNumber(String mobileNumber);
	List<User> findByFullnameRegexAndEmailRegexAndMobileNumberRegexAndAddressRegex(String fullname, String email,
																					String mobileNumber, String address);

}

package com.placetracker.PlaceTracker.repositories;

import com.placetracker.PlaceTracker.domain.PlaceSelfie;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface PlaceSelfieRepository  extends MongoRepository<PlaceSelfie, String> {
    List<PlaceSelfie> findByMentorMobileNumber(String mentorMobileNumber);
    List<PlaceSelfie> findByMobileNumber(String mobileNumber);
    List<PlaceSelfie> findByJobNameRegexAndMobileNumberRegexAndJobDescriptionRegex(String jobName, String mobileNumber,
                                                                                   String jobDescription);
    List<PlaceSelfie> findByJobNameRegexAndMobileNumberRegexAndFirstSelfieDateBetween(String jobName, String mobileNumber,
                                                                                      Date firstSelfieDateStart, Date firstSelfieDateEnd);
    List<PlaceSelfie> findByJobNameRegexAndMobileNumberRegexAndFirstSelfieDateAfter(String jobName, String mobileNumber,
                                                                                      Date firstSelfieDate);
    List<PlaceSelfie> findByJobNameRegexAndMobileNumberRegexAndFirstSelfieDateBefore(String jobName, String mobileNumber,
                                                                                      Date firstSelfieDate);
    List<PlaceSelfie> findByJobNameRegexAndMobileNumberRegex(String jobName, String mobileNumber);
    List<PlaceSelfie> findByJobNameRegexAndMobileNumberRegexAndJobDescriptionRegexAndMentorMobileNumberRegex(String jobName, String mobileNumber,
                                                                                   String jobDescription, String mentorMobileNumber);
}

package com.placetracker.PlaceTracker.services;

import com.placetracker.PlaceTracker.domain.PlaceSelfie;
import com.placetracker.PlaceTracker.repositories.PlaceSelfieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PlaceSelfieService {

    @Autowired
    PlaceSelfieRepository repository;

    public List<PlaceSelfie> getCreteriaPlaceSelfies(String jobName,
                                                     String mobileNumber,
                                                     String firstSelfieDateStart,
                                                     String firstSelfieDateEnd) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateBegin = null;
        Date dateEnd = null;
        try {
            dateBegin = simpleDateFormat.parse(firstSelfieDateStart);
        } catch (Exception e) {

        }

        try {
            dateEnd = simpleDateFormat.parse(firstSelfieDateEnd);
            Calendar cal = Calendar.getInstance(); // creates calendar
            cal.setTime(dateEnd); // sets calendar time/date
            cal.add(Calendar.HOUR_OF_DAY, 23); // adds one hour
            cal.add(Calendar.MINUTE, 59);
            dateEnd = cal.getTime();
        } catch (Exception e) {

        }

        if(dateBegin == null && dateEnd == null) {
            return repository.findByJobNameRegexAndMobileNumberRegex(jobName, mobileNumber);
        } else if(dateBegin != null && dateEnd == null){
            return repository.findByJobNameRegexAndMobileNumberRegexAndFirstSelfieDateAfter(jobName, mobileNumber, dateBegin);
        } else if(dateBegin == null && dateEnd != null){
            return repository.findByJobNameRegexAndMobileNumberRegexAndFirstSelfieDateAfter(jobName, mobileNumber, dateEnd);
        }
        return repository.findByJobNameRegexAndMobileNumberRegexAndFirstSelfieDateBetween(jobName, mobileNumber, dateBegin, dateEnd);
    }
}

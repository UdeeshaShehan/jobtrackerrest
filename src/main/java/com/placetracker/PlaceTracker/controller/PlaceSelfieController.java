package com.placetracker.PlaceTracker.controller;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.placetracker.PlaceTracker.domain.CSVPlcaselfie;
import com.placetracker.PlaceTracker.domain.Location;
import com.placetracker.PlaceTracker.domain.PlaceSelfie;
import com.placetracker.PlaceTracker.domain.User;
import com.placetracker.PlaceTracker.repositories.PlaceSelfieRepository;
import com.placetracker.PlaceTracker.services.CustomUserDetailsService;
import com.placetracker.PlaceTracker.services.PlaceSelfieService;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200",
        "http://jobtrackerusidtest-app.s3-website-ap-southeast-1.amazonaws.com" })
@RestController
@RequestMapping("/placeselfie")
@Api(value="onlinestore", description="Operations pertaining to products in Online Store")
public class PlaceSelfieController {
    @Autowired
    PlaceSelfieRepository repository;

    @Autowired
    PlaceSelfieService placeSelfieService;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    
    @GetMapping("/all")
    public List<PlaceSelfie> getAllPlaceSelfies() {
        return repository.findAll();
    }

    @GetMapping("/bycriteria")
    public List<PlaceSelfie> getCreteriaPlaceSelfies(@RequestParam("jobName")String jobName,
                                                     @RequestParam("mobileNumber")String mobileNumber,
                                                     @RequestParam("firstSelfieDateStart")String firstSelfieDateStart,
                                                     @RequestParam("firstSelfieDateEnd")String firstSelfieDateEnd) {

        return placeSelfieService.getCreteriaPlaceSelfies(jobName, mobileNumber, firstSelfieDateStart, firstSelfieDateEnd);
    }

    @GetMapping("/mobileNumber/{mobileNumber}")
    public List<PlaceSelfie> getplaceSelfiesByMobilrNumber(@PathVariable(value = "mobileNumber") String mobileNumber) {
        return repository.findByMobileNumber(mobileNumber);
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<PlaceSelfie> getSelfieById(@PathVariable(value = "id") String id) throws Exception{
        PlaceSelfie user =
                repository
                        .findById(id)
                        .orElseThrow(() -> new Exception());
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/add")
    public PlaceSelfie createSelfie(@Valid @RequestBody PlaceSelfie placeSelfie) throws Exception{
        User user=customUserDetailsService.findUserByMobileNumber(placeSelfie.getMobileNumber());
        if (user == null)
            throw new Exception();
        else {
            placeSelfie.setMentorMobileNumber(user.getMentorMobileNumber());
            placeSelfie.setUserName(user.getFullname());
        }
        return repository.save(placeSelfie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaceSelfie> updateSelfie(
            @PathVariable(value = "id") String id, @Valid @RequestBody PlaceSelfie userDetails)
            throws Exception {
        PlaceSelfie selfie =
                repository
                        .findById(id)
                        .orElseThrow(() -> new Exception());
        userDetails.setIsJobAdded(selfie.getIsJobAdded());
        userDetails.setLocations(selfie.getLocations());
        final PlaceSelfie updatedSelfie = repository.save(userDetails);
        return ResponseEntity.ok(updatedSelfie);
    }

    @PutMapping("/location/{id}")
    public ResponseEntity<PlaceSelfie> addLocation(
            @PathVariable(value = "id") String id, @Valid @RequestBody Location location)
            throws Exception {
        PlaceSelfie selfie =
                repository
                        .findById(id)
                        .orElseThrow(() -> new Exception());
        selfie.addLocation(location);
        final PlaceSelfie updatedSelfie = repository.save(selfie);
        return ResponseEntity.ok(updatedSelfie);
    }


    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteSelfie(@PathVariable(value = "id") String id) throws Exception {
        PlaceSelfie user =
                repository
                        .findById(id)
                        .orElseThrow(() -> new Exception());
        repository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @GetMapping("/exports")
    public void exportCSV(@RequestParam("jobName")String jobName,
                          @RequestParam("mobileNumber")String mobileNumber,
                          @RequestParam("firstSelfieDateStart")String firstSelfieDateStart,
                          @RequestParam("firstSelfieDateEnd")String firstSelfieDateEnd,
                          HttpServletResponse response) throws Exception {

        //set file name and content type
        String filename = "placedetails.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        //create a csv writer
        StatefulBeanToCsv<CSVPlcaselfie> writer = new StatefulBeanToCsvBuilder<CSVPlcaselfie>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false)
                .build();
        List<PlaceSelfie> placeSelfies = placeSelfieService.getCreteriaPlaceSelfies(jobName, mobileNumber, firstSelfieDateStart, firstSelfieDateEnd);
        List<CSVPlcaselfie> csvPlcaselfies = placeSelfies.stream().map(p -> {
            CSVPlcaselfie csvPlcaselfie = new CSVPlcaselfie();
            BeanUtils.copyProperties(p, csvPlcaselfie);
            if(p.getFirstLocation() != null) {
                csvPlcaselfie.setLatitude1(p.getFirstLocation().getLatitude());
                csvPlcaselfie.setLongitude1(p.getFirstLocation().getLongitude());
            }
            if(p.getLastLocation() != null) {
                csvPlcaselfie.setLatitude2(p.getLastLocation().getLatitude());
                csvPlcaselfie.setLongitude2(p.getLastLocation().getLongitude());
            }
            return csvPlcaselfie;
        }).collect(Collectors.toList());
        //write all users to csv file
        writer.write(csvPlcaselfies);

    }
}

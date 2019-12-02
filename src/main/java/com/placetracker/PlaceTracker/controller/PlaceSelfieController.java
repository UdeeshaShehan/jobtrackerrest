package com.placetracker.PlaceTracker.controller;

import com.placetracker.PlaceTracker.domain.Location;
import com.placetracker.PlaceTracker.domain.PlaceSelfie;
import com.placetracker.PlaceTracker.repositories.PlaceSelfieRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/placeselfie")
@Api(value="onlinestore", description="Operations pertaining to products in Online Store")
public class PlaceSelfieController {
    @Autowired
    PlaceSelfieRepository repository;

    
    @GetMapping("/all")
    public List<PlaceSelfie> getAllPlaceSelfies() {
        return repository.findAll();
    }

    @GetMapping("/email/{email}")
    public List<PlaceSelfie> getplaceSelfiesByEmail(@PathVariable(value = "email") String email) {
        return repository.findByEmail(email);
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
    public PlaceSelfie createUser(@Valid @RequestBody PlaceSelfie placeSelfie) {
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
}

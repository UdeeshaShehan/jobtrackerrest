package com.placetracker.PlaceTracker.repositories;

import com.placetracker.PlaceTracker.domain.PlaceSelfie;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PlaceSelfieRepository  extends MongoRepository<PlaceSelfie, String> {
    List<PlaceSelfie> findByEmail(String email);
}

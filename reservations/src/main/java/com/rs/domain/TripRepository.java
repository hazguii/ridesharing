package com.rs.domain;

import java.util.List;
import java.util.Optional;

import com.ddd.Repository;

public interface TripRepository extends Repository<Trip,TripId>{
    List<Trip> findAll();    
    List<Trip> findAllTripsWithNonNotifiedPassengers();
    List<Trip> findAllTripsWithNonNotifiedDrivers();
    void save(Trip Trip);
    Optional<Trip> lookup(TripId tripId);
    Optional<Trip> findTripByApplicationId(RSApplicationId rsApplicationId);
}

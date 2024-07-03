package com.rs.domain;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;
@Getter
public class RSApplication {

    RSApplicationId rsApplicationId;
    TripId tripId;
    boolean accepted=false;


    @JsonCreator
    public static RSApplication RSApplication( TripId tripId){
        return new RSApplication(tripId);
    }
    
    public RSApplication(RSApplicationId applicationId, TripId tripId,  boolean accepted) {
        this.rsApplicationId = applicationId;
        this.tripId = tripId;
        this.accepted=accepted;
    }

    public RSApplication(RSApplicationId applicationId, TripId tripId) {
        this.rsApplicationId = applicationId;
        this.tripId = tripId;
    }

    public RSApplication(TripId tripId){
        this(new RSApplicationId(UUID.randomUUID()), tripId);
    }

    public void setRsApplicationId(RSApplicationId rsApplicationId) {
        this.rsApplicationId = rsApplicationId;
    }

    public void setTripId(TripId tripId) {
        this.tripId = tripId;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public RSApplicationId getRsApplicationId() {
        return rsApplicationId;
    }

    public TripId getTripId() {
        return tripId;
    }

    public boolean isAccepted() {
        return accepted;
    }

    
}
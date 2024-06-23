package com.rs.application;

import java.time.LocalDateTime;

import com.rs.domain.RSApplicationId;
import com.rs.domain.TripId;
import com.rs.exceptions.CommandRejectedException;
import com.rs.exceptions.EntityNotFoundException;
import com.rs.domain.RSDemandId;
import com.rs.domain.RSOfferId;

public interface TripService {
    void create(RSOfferId rSOfferId, LocalDateTime departureTime);
    void acceptRSApplication(TripId tripId, RSApplicationId applicationId) throws EntityNotFoundException;
    void rejectRSApplication(TripId tripId, RSApplicationId applicationId) throws EntityNotFoundException;
    void submitRSApplication(TripId tripId,RSDemandId rsDemandId) throws CommandRejectedException, EntityNotFoundException;
    void cancelRSApplication(RSApplicationId rsApplicationId) throws CommandRejectedException,EntityNotFoundException;
    void cancelTrip(TripId tripId) throws CommandRejectedException,EntityNotFoundException;
    void notifyDriverBeforePickupDateArrives();
    void notifyPassengerBeforePickupDateArrives();
}

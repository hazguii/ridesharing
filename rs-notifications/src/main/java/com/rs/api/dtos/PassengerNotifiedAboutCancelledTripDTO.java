package com.rs.api.dtos;

import com.rs.domain.TripId;
import com.rs.domain.User;

public record PassengerNotifiedAboutCancelledTripDTO(User passenger, TripId tripId){
    
}

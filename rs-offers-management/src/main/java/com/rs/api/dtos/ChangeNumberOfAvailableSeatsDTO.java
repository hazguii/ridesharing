package com.rs.api.dtos;

import com.rs.domain.offer.NumberOfSeats;
import com.rs.domain.offer.RSOfferId;

public record ChangeNumberOfAvailableSeatsDTO(RSOfferId rsOfferId, NumberOfSeats availableSeatsNumber) {
    
}

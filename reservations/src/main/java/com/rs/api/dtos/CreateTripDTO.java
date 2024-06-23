package com.rs.api.dtos;

import java.time.LocalDateTime;

import com.rs.domain.RSOfferId;
import com.rs.domain.RSOfferPricingId;

public record CreateTripDTO(RSOfferId rsOfferId, LocalDateTime departureTime) {
    
}

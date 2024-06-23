package com.rs.api.dtos;

import java.time.LocalDateTime;
import com.rs.domain.offer.RSOfferId;

public record ChangeDepartureTimeDTO(RSOfferId rsOfferId, LocalDateTime departureDateTime) {
    
}

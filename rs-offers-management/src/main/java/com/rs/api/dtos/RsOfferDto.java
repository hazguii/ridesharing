package com.rs.api.dtos;

import com.rs.domain.offer.Address;
import com.rs.domain.offer.NumberOfSeats;
import com.rs.domain.offer.RSOfferId;
import com.rs.domain.offer.UserId;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RsOfferDto (RSOfferId rsOfferId, UserId userId, Address departureAddress, Address destinationAddress, NumberOfSeats numberOfAvailableSeats, LocalDateTime departureDateTime, boolean cancelled, boolean published, boolean closed){
}

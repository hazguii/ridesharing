package com.rs.api.dtos;

import java.time.LocalDateTime;

import com.rs.domain.offer.Address;
import com.rs.domain.offer.GeoPoint;
import com.rs.domain.offer.NumberOfSeats;

/**
 * CreateOffer
 */
public record CreateRSOfferDTO(Address departureAddress, GeoPoint departureGeoPoint, Address destinationAddress, GeoPoint destinationGeoPoint, NumberOfSeats availableSeatsNumber, LocalDateTime departureDateTime){
}
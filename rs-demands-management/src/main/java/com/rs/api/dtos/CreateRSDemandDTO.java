package com.rs.api.dtos;

import com.rs.domain.demand.Address;
import com.rs.domain.demand.GeoPoint;

import java.time.LocalDateTime;

public record CreateRSDemandDTO(Address departureAddress, GeoPoint departureGeoPoint, Address destinationAddress, GeoPoint destinationGeoPoint, LocalDateTime departureDateTime) {
}

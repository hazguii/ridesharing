package com.rs.application.services;

import com.rs.domain.demand.Address;
import com.rs.domain.demand.GeoPoint;
import com.rs.domain.demand.RSDemandId;
import com.rs.domain.demand.UserId;

import java.time.LocalDateTime;

public interface RSDemandsService {
    RSDemandId createRSDemand(UserId userId, Address departureAddress, GeoPoint departureGeoPoint, Address destinationAddress, GeoPoint destinationGeoPoint, LocalDateTime departureDateTime);
    void cancelRSDemand(RSDemandId rsDemandId);
    void closeRSDemand(RSDemandId rsDemandId);
    void publishRSDemand(RSDemandId rsDemandId);
    void changeRSDemandPickUpAddress(RSDemandId rsDemandId, Address newAddress, GeoPoint newGeoPoint);
    void changeRSDemandDepartureTime(RSDemandId rsDemandId,LocalDateTime departureDateTime);
    void closeExpiredRSDemands() ;
}

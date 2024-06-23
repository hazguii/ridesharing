package com.rs.application.events;

import com.ddd.DomainEvent;
import com.rs.application.Address;
import com.rs.application.RSDemandId;
import com.rs.domain.GeoPoint;

import java.time.LocalDateTime;
import java.util.UUID;

public record RSDemandPickUpAddressChanged(UUID eventId, LocalDateTime eventTime, RSDemandId rsDemandId, Address newAddress, GeoPoint newGeoPoint) implements DomainEvent {

    public RSDemandPickUpAddressChanged(RSDemandId rsDemandId, Address newAddress, GeoPoint newGeoPoint){
        this(UUID.randomUUID(), LocalDateTime.now(), rsDemandId, newAddress, newGeoPoint);
    }
    @Override
    public String aggregateId() {
        return rsDemandId.stringValue();
    }

    @Override
    public String aggregateType() {
        return "RSDemand";
    }

    @Override
    public String eventType() {
        return this.getClass().getSimpleName();
    }

}

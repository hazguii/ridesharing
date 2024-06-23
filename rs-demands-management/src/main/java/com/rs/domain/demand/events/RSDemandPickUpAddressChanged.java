package com.rs.domain.demand.events;
import com.ddd.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

import com.rs.domain.demand.GeoPoint;
import com.rs.domain.demand.RSDemandId;
import com.rs.domain.demand.Address;

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

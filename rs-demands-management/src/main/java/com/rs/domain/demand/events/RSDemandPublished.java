package com.rs.domain.demand.events;

import com.ddd.DomainEvent;
import com.rs.domain.demand.Address;
import com.rs.domain.demand.GeoPoint;
import com.rs.domain.demand.RSDemandId;
import com.rs.domain.demand.UserId;

import java.time.LocalDateTime;
import java.util.UUID;

public record RSDemandPublished(UUID eventId, LocalDateTime eventTime, RSDemandId rsDemandId, UserId userId, GeoPoint departureGeoPoint, GeoPoint destinationGeoPoint, LocalDateTime departureDateTime) implements DomainEvent {

    public RSDemandPublished(RSDemandId rsDemandId, UserId userId, GeoPoint departureGeoPoint, GeoPoint destinationGeoPoint, LocalDateTime departureDateTime){
        this(UUID.randomUUID(), LocalDateTime.now(), rsDemandId, userId, departureGeoPoint, destinationGeoPoint, departureDateTime);
    }
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

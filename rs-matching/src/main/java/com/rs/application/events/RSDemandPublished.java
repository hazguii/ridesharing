package com.rs.application.events;

import com.ddd.DomainEvent;
import com.rs.application.RSDemandId;
import com.rs.domain.GeoPoint;
import com.rs.domain.UserId;

import java.time.LocalDateTime;
import java.util.UUID;

public record RSDemandPublished(UUID eventId, LocalDateTime eventTime, RSDemandId rsDemandId, UserId userId, GeoPoint departureGeoPoint, GeoPoint destinationGeoPoint, LocalDateTime departureDateTime) implements DomainEvent {

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

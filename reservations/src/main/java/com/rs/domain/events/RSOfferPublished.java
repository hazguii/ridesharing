package com.rs.domain.events;

import com.ddd.DomainEvent;
import com.rs.domain.GeoPoint;
import com.rs.domain.RSOfferId;
import com.rs.domain.UserId;

import java.time.LocalDateTime;
import java.util.UUID;

public record RSOfferPublished(UUID eventId, LocalDateTime eventTime, RSOfferId rsOfferId, UserId userId, GeoPoint departureGeoPoint, GeoPoint destinationGeoPoint, LocalDateTime departureDateTime) implements DomainEvent {
    public RSOfferPublished(RSOfferId rsOfferId, UserId userId, GeoPoint departureGeoPoint, GeoPoint destinationGeoPoint, LocalDateTime departureDateTime){
        this(UUID.randomUUID(),LocalDateTime.now(),rsOfferId, userId, departureGeoPoint, destinationGeoPoint, departureDateTime);
    }
    @Override
    public String aggregateId() {
        return rsOfferId.stringValue();
    }
    @Override
    public String aggregateType() {
        return "RSOffer";
    }
    @Override
    public String eventType() {
        return "RSOfferPriced";
    }
}



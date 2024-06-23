package com.rs.domain.events;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ddd.DomainEvent;
import com.rs.domain.RSApplicationId;
import com.rs.domain.TripId;


public record RSApplicationRejected(UUID eventId, LocalDateTime eventTime, TripId tripId,RSApplicationId applicationId)implements DomainEvent{
    
    public RSApplicationRejected(TripId tripId,RSApplicationId applicationId) {
        this(UUID.randomUUID(),LocalDateTime.now(), tripId, applicationId);
    }
    @Override
    public String aggregateId() {
        return tripId.stringValue();
    }
    @Override
    public String aggregateType() {
        return "Trip";
    }
    @Override
    public String eventType() {
        return "RSApplicationRejected";
    }
}

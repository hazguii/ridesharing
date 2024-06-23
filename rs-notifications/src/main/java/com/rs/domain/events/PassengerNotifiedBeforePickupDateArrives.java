
package com.rs.domain.events;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ddd.DomainEvent;
import com.rs.domain.TripId;

public record PassengerNotifiedBeforePickupDateArrives(UUID eventId, LocalDateTime eventTime, TripId tripId)implements DomainEvent{
    
    public PassengerNotifiedBeforePickupDateArrives(TripId tripId) {
        this(UUID.randomUUID(),LocalDateTime.now(), tripId);
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
        return "PassengerNotified";
    }
}

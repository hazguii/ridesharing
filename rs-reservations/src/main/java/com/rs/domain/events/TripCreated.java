package com.rs.domain.events;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ddd.DomainEvent;
import com.rs.domain.TripId;

public record TripCreated(UUID eventId, LocalDateTime eventTime,TripId tripId) implements DomainEvent {

    

    public TripCreated(TripId tripId) {
        this(UUID.randomUUID(),LocalDateTime.now(),tripId);
    }

    @Override
    public String aggregateId() {
        // TODO Auto-generated method stub
        return tripId.stringValue();
    }

    @Override
    public String aggregateType() {
        // TODO Auto-generated method stub
        return "Trip";
    }

    @Override
    public String eventType() {
        // TODO Auto-generated method stub
        return "TripCreated";
    }
    
}


package com.rs.domain.pricing.events;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ddd.DomainEvent;
import com.rs.domain.pricing.RSOfferId;

public record DepartureTimeChanged(UUID eventId, LocalDateTime eventTime, RSOfferId rsOfferId, LocalDateTime departureDateTime) implements DomainEvent{

    public DepartureTimeChanged(RSOfferId rsOfferId, LocalDateTime departureDateTime) {
        this(UUID.randomUUID(),LocalDateTime.now(),rsOfferId, departureDateTime);
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
        return "DepartureTimeChanged";
    }
    
}

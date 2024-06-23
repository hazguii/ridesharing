
package com.rs.application.events;

import com.ddd.DomainEvent;
import com.rs.application.RSOfferId;

import java.time.LocalDateTime;
import java.util.UUID;

public record RSOfferDepartureTimeChanged(UUID eventId, LocalDateTime eventTime, RSOfferId rsOfferId, LocalDateTime newDepartureDateTime) implements DomainEvent{
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

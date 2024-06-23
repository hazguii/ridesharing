package com.rs.domain.events;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ddd.DomainEvent;
import com.rs.domain.RSOfferId;
import com.rs.domain.UserId;

public record RSOfferCreated(UUID eventId, LocalDateTime eventTime, UserId userId, RSOfferId rsOfferId) implements DomainEvent{
    
    @Override
    public String aggregateId() {
        return rsOfferId.toString();
    }
    @Override
    public String aggregateType() {
        return "RSOffer";
    }
    @Override
    public String eventType() {
        return "RSOfferCreated";
    }
    
}

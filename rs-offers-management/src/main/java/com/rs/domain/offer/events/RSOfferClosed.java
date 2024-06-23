package com.rs.domain.offer.events;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ddd.DomainEvent;
import com.rs.domain.offer.RSOfferId;

public record RSOfferClosed(UUID eventId, LocalDateTime eventTime, RSOfferId rsOfferId) implements DomainEvent{

    public RSOfferClosed(RSOfferId rsOfferId) {
        this(UUID.randomUUID(),LocalDateTime.now(),rsOfferId);
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
        return "RSOfferClosed";
    }
    
}

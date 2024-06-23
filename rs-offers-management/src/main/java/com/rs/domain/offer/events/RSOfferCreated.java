package com.rs.domain.offer.events;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ddd.DomainEvent;
import com.rs.domain.offer.RSOfferId;
import com.rs.domain.offer.UserId;

public record RSOfferCreated(UUID eventId, LocalDateTime eventTime, UserId userId, RSOfferId rsOfferId) implements DomainEvent{

    public RSOfferCreated(UserId userId, RSOfferId rsOfferId) {
        this(UUID.randomUUID(),LocalDateTime.now(),userId,rsOfferId);
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
        return "RSOfferCreated";
    }
    
}

package com.rs.domain.events;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ddd.DomainEvent;
import com.rs.domain.RSOfferId;

public record RSOfferPriced(UUID eventId, LocalDateTime eventTime, RSOfferId rsOfferId) implements DomainEvent {
    public RSOfferPriced(RSOfferId rsOfferId){
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
        return "RSOfferPriced";
    }
}



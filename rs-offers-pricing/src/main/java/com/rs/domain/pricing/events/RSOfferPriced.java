package com.rs.domain.pricing.events;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ddd.DomainEvent;
import com.rs.domain.pricing.RSOfferId;

public record RSOfferPriced(UUID eventId, LocalDateTime eventTime, RSOfferId rsOfferId, double price) implements DomainEvent {
    public RSOfferPriced(RSOfferId rsOfferId, double price){
        this(UUID.randomUUID(),LocalDateTime.now(),rsOfferId, price);
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



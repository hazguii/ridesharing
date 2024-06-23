package com.rs.domain.pricing.events;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ddd.DomainEvent;
import com.rs.domain.pricing.RSOfferId;


public record RSOfferPublished(UUID eventId, LocalDateTime eventTime, RSOfferId rsOfferId) implements DomainEvent{
    public RSOfferPublished(RSOfferId rsOfferId){
        this(UUID.randomUUID(),LocalDateTime.now(),rsOfferId);
    }
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
        return "RSOfferPublished";
    }
    
}

package com.rs.domain.events;

import com.ddd.DomainEvent;
import com.rs.application.RSOfferId;

import java.time.LocalDateTime;
import java.util.UUID;

public record RSOfferMatchRequestStopped(UUID eventId, LocalDateTime eventTime, RSOfferId rsOfferMatchRequestId) implements DomainEvent {
    public RSOfferMatchRequestStopped(RSOfferId rsOfferMatchRequestId){
        this(UUID.randomUUID(), LocalDateTime.now(), rsOfferMatchRequestId);
    }
    @Override
    public String aggregateId() {
        return rsOfferMatchRequestId.value().toString();
    }

    @Override
    public String aggregateType() {
        return "RSOfferMatchRequest";
    }

    @Override
    public String eventType() {
        return "RSOfferMatchRequestStopped";
    }
}
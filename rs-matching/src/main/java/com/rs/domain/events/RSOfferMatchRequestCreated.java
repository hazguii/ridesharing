package com.rs.domain.events;

import com.ddd.DomainEvent;
import com.rs.application.RSOfferId;
import com.rs.domain.RSOfferMatchRequest;

import java.time.LocalDateTime;
import java.util.UUID;

public record RSOfferMatchRequestCreated(UUID eventId, LocalDateTime eventTime, RSOfferId rsOfferMatchRequestId) implements DomainEvent {
    public RSOfferMatchRequestCreated(RSOfferId rsOfferId){
        this(UUID.randomUUID(), LocalDateTime.now(), rsOfferId);
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
        return "RSOfferMatchRequestCreated";
    }
}

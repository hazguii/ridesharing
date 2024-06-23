package com.rs.domain.events;

import com.ddd.DomainEvent;
import com.rs.application.RSDemandId;
import com.rs.application.RSOfferId;

import java.time.LocalDateTime;
import java.util.UUID;

public record RSOfferMatchRequestDepartureTimeChanged(UUID eventId, LocalDateTime eventTime, RSOfferId rsOfferId) implements DomainEvent {
    public RSOfferMatchRequestDepartureTimeChanged(RSOfferId rsOfferId){
        this(UUID.randomUUID(), LocalDateTime.now(), rsOfferId);
    }
    @Override
    public String aggregateId() {
        return rsOfferId.value().toString();
    }

    @Override
    public String aggregateType() {
        return "RSOfferMatchRequest";
    }

    @Override
    public String eventType() {
        return "RSOfferMatchRequestDepartureTimeChanged";
    }
}

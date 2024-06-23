package com.rs.domain.events;

import com.ddd.DomainEvent;
import com.rs.application.RSDemandId;

import java.time.LocalDateTime;
import java.util.UUID;

public record RSDemandMatchRequestStopped(UUID eventId, LocalDateTime eventTime, RSDemandId rsDemandMatchRequestId) implements DomainEvent {
    public RSDemandMatchRequestStopped(RSDemandId rsDemandMatchRequestId){
        this(UUID.randomUUID(), LocalDateTime.now(),rsDemandMatchRequestId);
    }
    @Override
    public String aggregateId() {
        return rsDemandMatchRequestId.value().toString();
    }

    @Override
    public String aggregateType() {
        return "RSDemandMatchRequest";
    }

    @Override
    public String eventType() {
        return "RSDemandMatchRequestStopped";
    }
}
package com.rs.application.events;

import com.ddd.DomainEvent;
import com.rs.application.RSDemandId;

import java.time.LocalDateTime;
import java.util.UUID;

public record RSDemandClosed(UUID eventId, LocalDateTime eventTime, RSDemandId rsDemandId) implements DomainEvent {

    public RSDemandClosed(RSDemandId rsDemandId){
        this(UUID.randomUUID(), LocalDateTime.now(), rsDemandId);
    }
    public String aggregateId() {
        return rsDemandId.stringValue();
    }

    @Override
    public String aggregateType() {
        return "RSDemand";
    }

    @Override
    public String eventType() {
        return this.getClass().getSimpleName();
    }
}

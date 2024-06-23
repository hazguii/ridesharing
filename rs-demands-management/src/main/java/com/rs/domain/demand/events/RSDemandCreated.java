package com.rs.domain.demand.events;

import com.ddd.DomainEvent;
import com.rs.domain.demand.RSDemandId;
import com.rs.domain.demand.UserId;

import java.time.LocalDateTime;
import java.util.UUID;

public record RSDemandCreated(UUID eventId, LocalDateTime eventTime, UserId userId, RSDemandId rsDemandId) implements DomainEvent {

    public RSDemandCreated(UserId userId, RSDemandId rsDemandId){
        this(UUID.randomUUID(), LocalDateTime.now(), userId, rsDemandId);
    }
    @Override
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

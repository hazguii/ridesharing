package com.rs.domain.events;

import com.ddd.DomainEvent;
import com.rs.domain.RSDemandId;

import java.time.LocalDateTime;
import java.util.UUID;

public record RSDemandCancelled(UUID eventId, LocalDateTime eventTime, RSDemandId rsDemandId) implements DomainEvent {

    public RSDemandCancelled(RSDemandId rsDemandId){
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

package com.rs.domain.demand.events;

import com.ddd.DomainEvent;
import com.rs.domain.demand.RSDemandId;

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

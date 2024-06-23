package com.rs.domain.demand.events;

import com.ddd.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;
import com.rs.domain.demand.RSDemandId;

public record RSDemandDepartureTimeChanged(UUID eventId, LocalDateTime eventTime, RSDemandId rsDemandId, LocalDateTime newDepartureDateTime) implements DomainEvent {

    public RSDemandDepartureTimeChanged(RSDemandId rsDemandId, LocalDateTime newDepartureDateTime){
        this(UUID.randomUUID(), LocalDateTime.now(), rsDemandId, newDepartureDateTime);
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

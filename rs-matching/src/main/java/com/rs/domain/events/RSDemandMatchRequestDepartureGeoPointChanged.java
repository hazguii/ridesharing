package com.rs.domain.events;

import com.ddd.DomainEvent;
import com.rs.application.RSDemandId;

import java.time.LocalDateTime;
import java.util.UUID;

public record RSDemandMatchRequestDepartureGeoPointChanged(UUID eventId, LocalDateTime eventTime, RSDemandId rsDemandMatchRequestId) implements DomainEvent {
    public RSDemandMatchRequestDepartureGeoPointChanged(RSDemandId rsDemandId){
        this(UUID.randomUUID(), LocalDateTime.now(), rsDemandId);
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
        return "RSDemandMatchRequestDepartureGeoPointChanged";
    }
}

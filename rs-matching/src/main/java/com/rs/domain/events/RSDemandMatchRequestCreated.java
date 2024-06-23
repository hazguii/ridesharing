package com.rs.domain.events;

import com.ddd.DomainEvent;
import com.rs.application.RSDemandId;
import com.rs.domain.RSDemandMatchRequest;

import java.time.LocalDateTime;
import java.util.UUID;

public record RSDemandMatchRequestCreated(UUID eventId, LocalDateTime eventTime, RSDemandId rsDemandMatchRequestId) implements DomainEvent {
    public RSDemandMatchRequestCreated(RSDemandId rsDemandId){
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
        return "RSDemandMatchRequestCreated";
    }
}

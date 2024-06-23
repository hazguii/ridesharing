package com.rs.domain.events;

import com.ddd.DomainEvent;
import com.rs.application.RSDemandId;
import com.rs.application.RSOfferId;

import java.time.LocalDateTime;
import java.util.UUID;

public record RSMatchFound(UUID eventId, LocalDateTime eventTime, RSDemandId rsDemandId, RSOfferId rsOfferId) implements DomainEvent {
    public RSMatchFound(RSDemandId rsDemandId, RSOfferId rsOfferId){
        this(UUID.randomUUID(), LocalDateTime.now(), rsDemandId, rsOfferId);
    }
    @Override
    public String aggregateId() {
        return null;
    }

    @Override
    public String aggregateType() {
        return null;
    }

    @Override
    public String eventType() {
        return "RSMatchFound";
    }
}

package com.rs.domain.events;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ddd.DomainEvent;
import com.rs.domain.User;

public record PassengerReminded (UUID eventId, User passenger)implements DomainEvent{
    public PassengerReminded(User passenger) {
        this(UUID.randomUUID(),passenger);
    }

    @Override
    public String aggregateId() {
        return passenger.userId().toString();
    }
    @Override
    public String aggregateType() {
        return "Email";
    }
    @Override
    public String eventType() {
        return "EmailCreated";
    }

    @Override
    public LocalDateTime eventTime() {
        // TODO Auto-generated method stub
        return null;
    }
}

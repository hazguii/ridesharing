package com.rs.domain.events;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ddd.DomainEvent;
import com.rs.domain.User;

public record PassengerNotifiedAboutCancelledTrip (UUID eventId, User driver)implements DomainEvent{
    public PassengerNotifiedAboutCancelledTrip(User driver) {
        this(UUID.randomUUID(),driver);
    }

    @Override
    public String aggregateId() {
        return driver.userId().toString();
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

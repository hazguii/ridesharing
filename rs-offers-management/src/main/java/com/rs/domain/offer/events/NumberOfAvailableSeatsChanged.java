package com.rs.domain.offer.events;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ddd.DomainEvent;
import com.rs.domain.offer.NumberOfSeats;
import com.rs.domain.offer.RSOfferId;

public record NumberOfAvailableSeatsChanged(UUID eventId, LocalDateTime eventTime, RSOfferId rsOfferId, NumberOfSeats numberOfSeats) implements DomainEvent{

    public NumberOfAvailableSeatsChanged(RSOfferId rsOfferId, NumberOfSeats numberOfSeats) {
        this(UUID.randomUUID(),LocalDateTime.now(),rsOfferId, numberOfSeats);
    }
    @Override
    public String aggregateId() {
        return rsOfferId.stringValue();
    }
    @Override
    public String aggregateType() {
        return "RSOffer";
    }
    @Override
    public String eventType() {
        return "NumberOfAvailableSeatsChanged";
    }
    
}



package com.rs.domain.events;
import com.ddd.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;
import com.rs.domain.RSDemandId;
import com.rs.domain.Address;

public record RSDemandPickUpAddressChanged(UUID eventId, LocalDateTime eventTime, RSDemandId rsDemandId, Address newAddress) implements DomainEvent {

    public RSDemandPickUpAddressChanged(RSDemandId rsDemandId, Address newAddress){
        this(UUID.randomUUID(), LocalDateTime.now(), rsDemandId, newAddress);
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

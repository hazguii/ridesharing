package com.rs.domain.events;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ddd.DomainEvent;
import com.rs.domain.RSApplicationId;
import com.rs.domain.TripId;

public record RSApplicationSubmitted(UUID eventId, LocalDateTime eventTime, TripId tripId,RSApplicationId applicationId)  implements DomainEvent{

    public RSApplicationSubmitted(TripId tripId,RSApplicationId applicationId){
        this(UUID.randomUUID(),LocalDateTime.now(),tripId, applicationId);
    }

    @Override
    public String aggregateId() {
        // TODO Auto-generated method stub
        return applicationId.toString();
    }

    @Override
    public String aggregateType() {
        // TODO Auto-generated method stub
        return "RsApplication";
    }

    @Override
    public String eventType() {
        // TODO Auto-generated method stub
        return "RsApplicationSubmitted";
    }
    
}

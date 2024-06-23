package com.rs.domain.offer;

import java.util.UUID;

import com.ddd.AggregateId;

/**
 * OfferId
 */
public record RSOfferId(UUID  value) implements AggregateId {

    
    public RSOfferId(){
        this(UUID.randomUUID());
    }

    @Override
    public String stringValue() {
        return value.toString();
    }
    
}
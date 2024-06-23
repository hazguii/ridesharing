package com.rs.application;

import com.ddd.AggregateId;

import java.util.UUID;

/**
 * OfferId
 */
public record RSOfferId(UUID  value) implements AggregateId {

    public boolean equals(RSOfferId offerId){
        return this.value == offerId.value;
    }
    
    public RSOfferId(){
        this(UUID.randomUUID());
    }

    @Override
    public String stringValue() {
        return value.toString();
    }
    
}
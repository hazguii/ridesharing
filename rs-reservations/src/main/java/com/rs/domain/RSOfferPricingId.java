package com.rs.domain;

import java.util.UUID;

import com.ddd.AggregateId;

/**
 * OfferId
 */
public record RSOfferPricingId(UUID  value) implements AggregateId {

    
    public RSOfferPricingId(){
        this(UUID.randomUUID());
    }

    @Override
    public String stringValue() {
        return value.toString();
    }
    
}
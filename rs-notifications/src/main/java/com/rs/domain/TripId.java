package com.rs.domain;

import java.util.UUID;

import com.ddd.AggregateId;

public record TripId(UUID value) implements AggregateId{
    @Override
    public String stringValue(){
        return value.toString();
    }
}

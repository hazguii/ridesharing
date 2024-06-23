package com.rs.domain.demand;

import com.ddd.AggregateId;

import java.util.UUID;

public record RSDemandId(UUID value) implements AggregateId {
    @Override
    public String stringValue() {
        return this.value.toString();
    }

    public RSDemandId(){
        this(UUID.randomUUID());
    }
}

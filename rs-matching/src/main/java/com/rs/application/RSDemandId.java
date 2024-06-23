package com.rs.application;

import com.ddd.AggregateId;

import java.util.UUID;

public record RSDemandId(UUID value) implements AggregateId {
    @Override
    public String stringValue() {
        return this.value.toString();
    }

    public boolean equals(RSDemandId demandId){
        return this.value == demandId.value;
    }

    public RSDemandId(){
        this(UUID.randomUUID());
    }
}

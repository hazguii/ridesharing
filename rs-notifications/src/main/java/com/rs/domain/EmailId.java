package com.rs.domain;

import java.util.UUID;

import com.ddd.AggregateId;

public record EmailId(UUID id) implements AggregateId{
    public EmailId(){
        this(UUID.randomUUID());
    }

    @Override
    public String stringValue() {
        return id.toString();
    }
}

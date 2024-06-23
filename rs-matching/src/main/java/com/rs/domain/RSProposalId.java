package com.rs.domain;

import com.ddd.AggregateId;

import java.util.UUID;

public record RSProposalId(UUID value) implements AggregateId {
    @Override
    public String stringValue() {
        return value.toString();
    }
    public RSProposalId(){this(UUID.randomUUID());}
}

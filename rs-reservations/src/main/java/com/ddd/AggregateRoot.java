package com.ddd;

public interface AggregateRoot<AID extends AggregateId> {
    AID id();
}

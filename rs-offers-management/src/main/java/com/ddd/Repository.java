package com.ddd;

import java.util.Optional;

public interface Repository<A extends AggregateRoot<ID>, ID extends AggregateId> {
    A save(A aggregate);
    Optional<A> lookup(ID id);
}

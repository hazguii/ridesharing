package com.ddd;

import java.util.Optional;

public interface Repository<A extends AggregateRoot<ID>,ID extends AggregateId> {
    void save(A aggregate);
    Optional<A> lookUp(ID id);
}

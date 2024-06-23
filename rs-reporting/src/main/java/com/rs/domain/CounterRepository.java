package com.rs.domain;

import java.util.List;
import java.util.Optional;

public interface CounterRepository{
    Optional<Counter> findCounterByName(String counterName);
    List<Counter> findAll();
    void incrementCounterValue(String counterName);
    void decrementCounterValue(String counterName);
}

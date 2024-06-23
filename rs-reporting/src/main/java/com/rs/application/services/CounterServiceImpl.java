package com.rs.application.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.rs.domain.CounterRepository;


@ApplicationScoped
public class CounterServiceImpl implements CounterService {
    @Inject
    CounterRepository counterRepository;


    @Override
    public void incrementCounter(String couterName) {
        counterRepository.incrementCounterValue(couterName);
    }
    @Override
    public void decrementCounter(String couterName) {
        counterRepository.decrementCounterValue(couterName);
    }
    
}

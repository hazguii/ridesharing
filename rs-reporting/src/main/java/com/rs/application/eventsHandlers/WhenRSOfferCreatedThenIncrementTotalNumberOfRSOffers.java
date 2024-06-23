package com.rs.application.eventsHandlers;

import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import com.ddd.EventHandler;
import com.rs.domain.CounterRepository;
import com.rs.domain.events.RSOfferCreated;

public class WhenRSOfferCreatedThenIncrementTotalNumberOfRSOffers implements EventHandler<RSOfferCreated>{

    @Inject
    CounterRepository counterRepository;

    @Incoming("rsoffer-created")
    @Override
    public void handle(RSOfferCreated event) {
        counterRepository.incrementCounterValue("rsoffer-total");
    }
    
}

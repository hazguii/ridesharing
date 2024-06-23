package com.rs.application.eventsHandlers;

import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import com.ddd.EventHandler;
import com.rs.application.TripService;
import com.rs.domain.TripId;
import com.rs.domain.events.RSOfferCancelled;
import com.rs.exceptions.CommandRejectedException;
import com.rs.exceptions.EntityNotFoundException;

public class WhenRSOfferCancelledCancelTrip implements EventHandler<RSOfferCancelled>{

    @Inject
    TripService tripService;

    @Incoming("rsoffer-cancelled")
    @Override
    public void handle(RSOfferCancelled event) throws CommandRejectedException,EntityNotFoundException{
        tripService.cancelTrip(new TripId(event.rsOfferId().value()));
    }

}



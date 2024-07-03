package com.rs.application.eventsHandlers;

import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import com.ddd.EventHandler;
import com.rs.application.TripService;
import com.rs.domain.events.RSOfferPublished;

public class WhenRSOfferPublishedCreateTrip implements EventHandler<RSOfferPublished>{

    @Inject
    TripService tripService;

    
    @Incoming("rsoffer-published")
    @Override
    public void handle(RSOfferPublished event) {
        tripService.create(event.rsOfferId(), event.departureDateTime());
    }

  
}



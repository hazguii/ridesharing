package com.rs.application.eventsHandlers;

import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import com.ddd.EventHandler;
import com.rs.application.TripService;
import com.rs.domain.RSApplicationId;
import com.rs.domain.events.RSDemandCancelled;
import com.rs.exceptions.CommandRejectedException;
import com.rs.exceptions.EntityNotFoundException;

public class WhenRSDemandCancelledCancelApplication implements EventHandler<RSDemandCancelled>{

    @Inject
    TripService tripService;

    @Incoming("rsdemand-cancelled")
    @Override
    public void handle(RSDemandCancelled event) throws CommandRejectedException,EntityNotFoundException{
        tripService.cancelRSApplication(new RSApplicationId(event.rsDemandId().value()));
    }
    

}
 


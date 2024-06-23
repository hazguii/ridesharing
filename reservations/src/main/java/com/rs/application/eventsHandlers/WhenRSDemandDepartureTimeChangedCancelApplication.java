package com.rs.application.eventsHandlers;

import com.ddd.EventHandler;

import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import com.rs.application.TripService;
import com.rs.domain.RSApplicationId;
import com.rs.domain.events.RSDemandDepartureTimeChanged;
import com.rs.exceptions.CommandRejectedException;
import com.rs.exceptions.EntityNotFoundException;
public class WhenRSDemandDepartureTimeChangedCancelApplication implements EventHandler<RSDemandDepartureTimeChanged>{

    @Inject
    TripService tripService;

    @Incoming("rsdemand-departure-time-changed")
    @Override
    public void handle(RSDemandDepartureTimeChanged event) throws CommandRejectedException,EntityNotFoundException{
        tripService.cancelRSApplication(new RSApplicationId(event.rsDemandId().value()));
    }
    

}
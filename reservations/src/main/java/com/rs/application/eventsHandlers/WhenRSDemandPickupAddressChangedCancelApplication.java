package com.rs.application.eventsHandlers;
import com.ddd.EventHandler;

import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import com.rs.application.TripService;
import com.rs.domain.RSApplicationId;
import com.rs.domain.events.RSDemandPickUpAddressChanged;
import com.rs.exceptions.CommandRejectedException;
import com.rs.exceptions.EntityNotFoundException;
public class WhenRSDemandPickupAddressChangedCancelApplication implements EventHandler<RSDemandPickUpAddressChanged>{

    @Inject
    TripService tripService;

    @Incoming("rsdemand-pickup-address-changed")
    @Override
    public void handle(RSDemandPickUpAddressChanged event) throws CommandRejectedException,EntityNotFoundException{
        tripService.cancelRSApplication(new RSApplicationId(event.rsDemandId().value()));
    }
    

}
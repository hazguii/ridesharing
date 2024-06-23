package com.rs.application.eventsHandlers;

import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import com.ddd.EventHandler;
import com.rs.application.services.EmailService;
import com.rs.domain.EmailRepository;
import com.rs.domain.User;
import com.rs.domain.events.TripCancelled;

import io.smallrye.reactive.messaging.annotations.Blocking;


public class WhenTripCancelledThenNotifyPassenger implements EventHandler<TripCancelled>{

    @Inject
    EmailService emailService;
    @Inject
    EmailRepository emailRepository;

    @Incoming("trip-cancelled")
    @Blocking
    @Override
    public void handle(TripCancelled event) {
        emailService.notifyPassengerAboutCancelledTrip(new User("hazgui","ahmed","ahmed.hazgui@etudiant-enit.utm.tn"), event.tripId());
    }    
}

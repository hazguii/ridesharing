package com.rs.application.eventsHandlers;

import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import com.ddd.EventHandler;
import com.rs.application.services.EmailService;
import com.rs.domain.EmailRepository;
import com.rs.domain.User;
import com.rs.domain.events.RSApplicationCancelled;

import io.smallrye.reactive.messaging.annotations.Blocking;


public class SendReminderToPassenger implements EventHandler<RSApplicationCancelled>{

    @Inject
    EmailService emailService;
    @Inject
    EmailRepository emailRepository;

    @Incoming("passenger-notified-before-pickup-date-arrived")
    @Blocking
    @Override
    public void handle(RSApplicationCancelled event) {
        emailService.remindPassenger(new User("hazgui","ahmed","ahmed.hazgui@etudiant-enit.utm.tn"), event.tripId());
    }    
}

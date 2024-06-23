package com.rs.application.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.rs.domain.*;
import com.rs.domain.events.DriverNotifiedAboutCancelledApplication;
import com.rs.domain.events.DriverReminded;
import com.rs.domain.events.PassengerNotifiedAboutCancelledTrip;
import com.rs.domain.events.PassengerReminded;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.scheduler.Scheduled;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;



@ApplicationScoped
public class EmailServiceImpl implements EmailService {
    @Inject
    EmailRepository EmailRepository;

    @Inject 
    Mailer mailer;    

    
    @Inject
    @Channel("driver-notified-about-cancelled-application")
    Emitter<DriverNotifiedAboutCancelledApplication> driverNotifiedAboutCancelledApplicationEmitter;

    @Inject
    @Channel("passenger-notified-about-cancelled-trip")
    Emitter<PassengerNotifiedAboutCancelledTrip> passengerNotifiedAboutCancelledTripEmitter;

    @Inject
    @Channel("passenger-reminded")
    Emitter<PassengerReminded> passengerRemindedEmitter;

    @Inject
    @Channel("driver-reminded")
    Emitter<DriverReminded> driverRemindedEmitter;

    @Override
    public User notifyDriverAboutCancelledApplication(User driver, RSApplicationId rsApplicationId) {
        
        EmailRepository.notifyDriverAboutCancelledApplication(driver,rsApplicationId);
        driverNotifiedAboutCancelledApplicationEmitter.send(new DriverNotifiedAboutCancelledApplication(driver));
        return driver;
    }

    @Override
    public User notifyPassengerAboutCancelledTrip(User passenger, TripId tripId) {
        
        EmailRepository.notifyPassengerAboutCancelledTrip(passenger,tripId);
        passengerNotifiedAboutCancelledTripEmitter.send(new PassengerNotifiedAboutCancelledTrip(passenger));
        return passenger;
    }


    @Override
    @Scheduled(every = "30s")
    public void sendUnsentEmails() {
        List<Email> unsentEmails = EmailRepository.findAllUnsentEmails();
        for(Email email:unsentEmails){
            mailer.send(Mail.withHtml(email.getUser().emailAddress(), email.getObject(), email.getBody()));
            email.sent();
            EmailRepository.save(email);
        }
    }

    @Override
    public void remindDriver(User driver, TripId tripId) {
        EmailRepository.remindDriver(driver,tripId);
        driverRemindedEmitter.send(new DriverReminded(driver));
    }

    @Override
    public void remindPassenger(User passenger, TripId tripId) {
        EmailRepository.remindPassenger(passenger,tripId);
        passengerRemindedEmitter.send(new PassengerReminded(passenger));

    }

   
    
}

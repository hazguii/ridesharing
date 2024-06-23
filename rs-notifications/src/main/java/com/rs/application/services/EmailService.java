package com.rs.application.services;
import com.rs.domain.*;


public interface EmailService {
    User notifyDriverAboutCancelledApplication(User driver,RSApplicationId rsApplicationId);
    User notifyPassengerAboutCancelledTrip(User passenger,TripId tripId);
    void sendUnsentEmails();
    void remindDriver(User driver, TripId tripId);
    void remindPassenger(User passenger, TripId tripId);
}

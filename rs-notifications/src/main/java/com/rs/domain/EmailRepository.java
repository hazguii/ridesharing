package com.rs.domain;

import java.util.List;

public interface EmailRepository{
    List<Email> findAll() ;
    List<Email> findAllUnsentEmails();
    void notifyDriverAboutCancelledApplication(User driver, RSApplicationId rsApplicationId);
    void notifyPassengerAboutCancelledTrip(User passenger,TripId tripId);
    void remindDriver(User driver, TripId tripId);
    void remindPassenger(User passenger, TripId tripId);
    void save(Email email);
}

package com.rs.api;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;


import com.rs.api.dtos.DriverNotifiedAboutCancelledApplicationDTO;
import com.rs.api.dtos.PassengerNotifiedAboutCancelledTripDTO;
import com.rs.application.services.EmailService;
import com.rs.domain.Email;
import com.rs.domain.EmailRepository;
import com.rs.domain.User;

@Path("/notifications")
public class EmailResource {

    @Inject
    EmailService emailService;


    @Inject
    EmailRepository emailRepository;

    @GET
    public List<Email> findAll()  {
        return emailRepository.findAll();
    }

    @POST  
    @Path("/notify-driver-about-cancelled-application")                                                              
    public void notifyDriverAboutCancelledApplication(DriverNotifiedAboutCancelledApplicationDTO dto) {
        emailService.notifyDriverAboutCancelledApplication(new User("ahmed", "hazgui", "ahmed.hazgui@etudiant-enit.utm.tn"), dto.rsApplicationId());
    }

    @POST  
    @Path("/notify-passenger-about-cancelled-trip")                                                              
    public void notifyPassengerAboutCancelledTrip(PassengerNotifiedAboutCancelledTripDTO dto) {
        emailService.notifyPassengerAboutCancelledTrip(new User("ahmed", "hazgui", "ahmed.hazgui@etudiant-enit.utm.tn"), dto.tripId());
    }
}
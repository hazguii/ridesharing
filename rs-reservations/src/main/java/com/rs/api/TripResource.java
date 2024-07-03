package com.rs.api;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.rs.api.dtos.AcceptRSApplicationDTO;
import com.rs.api.dtos.CancelRsApplicationDTO;
import com.rs.api.dtos.CancelTripDTO;
import com.rs.api.dtos.CreateTripDTO;
import com.rs.api.dtos.RejectRSApplicationDTO;
import com.rs.api.dtos.SubmitRSApplicationDTO;
import com.rs.application.TripService;
import com.rs.domain.Trip;
import com.rs.domain.TripRepository;
import com.rs.exceptions.CommandRejectedException;
import com.rs.exceptions.EntityNotFoundException;

@Path("/trips")

public class TripResource {

    @Inject
    TripRepository tripRepository;
    @Inject 
    TripService tripService;
    
    
    
    @GET
    public List<Trip> findAll() {
        return tripRepository.findAll();
    }
    @POST
    public void createTrip(CreateTripDTO dto){
        tripService.create(dto.rsOfferId(), dto.departureTime());
    }

    @POST
    @Path("/accept")
    public void acceptRSApplication(AcceptRSApplicationDTO dto)throws EntityNotFoundException{
        tripService.acceptRSApplication(dto.tripId(), dto.applicationId());
    }

    @POST
    @Path("/reject")
    public void rejectRSApplication(RejectRSApplicationDTO dto)throws EntityNotFoundException{
        tripService.rejectRSApplication(dto.tripId(), dto.applicationId());
    }
    @POST
    @Path("/submit")
    public void submitRSApplication(SubmitRSApplicationDTO dto)throws EntityNotFoundException,CommandRejectedException{
        tripService.submitRSApplication(dto.tripId(),dto.rsDemandId());
    }
    @POST
    @Path("/cancelApplication")
    public void cancelRSApplication(CancelRsApplicationDTO dto)throws EntityNotFoundException,CommandRejectedException{
        tripService.cancelRSApplication(dto.rsApplicationId());
    }
    @POST
    @Path("/cancelTrip")
    public void cancelTrip(CancelTripDTO dto)throws EntityNotFoundException,CommandRejectedException{
        tripService.cancelTrip(dto.tripId());
    }
}

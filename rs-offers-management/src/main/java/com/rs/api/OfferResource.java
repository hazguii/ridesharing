package com.rs.api;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.rs.api.dtos.*;
import com.rs.application.services.RSOfferService;
import com.rs.domain.offer.RSOffer;
import com.rs.domain.offer.RSOfferId;
import com.rs.domain.offer.RSOfferRepository;
import com.rs.domain.offer.UserId;
import com.rs.exceptions.CommandRejectedException;
import com.rs.exceptions.EntityNotFoundException;


@Path("/rsOffers")
public class OfferResource {

    @Inject
    RSOfferService rsOfferService;
    

    @Inject
    RSOfferRepository offersRepository;

    @GET
    public List<RsOfferDto> findAll() {
        return rsOfferService.getAllRsOffers();
    }
    @GET
    @Path("/listAdmin")
    @RolesAllowed("rider")
    public Response findAllAdmin() {
        return Response.ok(rsOfferService.getAllRsOffers()).build();
    }

    @GET
    @Path("/{rsOfferId}")
    public Response findById(@PathParam("rsOfferId") UUID id) {
        Optional<RSOffer> offer = offersRepository.lookup(new RSOfferId(id));
        if(offer.isPresent()){
            return Response.accepted(offer.get()).build();
        }else{
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @POST
    public RSOfferId createRSOffer(CreateRSOfferDTO dto) throws EntityNotFoundException{
        return rsOfferService.createRSOffer(new UserId(UUID.randomUUID()), dto.departureAddress(),
                                            dto.destinationAddress(), dto.availableSeatsNumber(), dto.departureDateTime());
    }
    
    @POST
    @Path("/cancel")
    public void cancelRSOffer(CancelRSOfferDTO dto) throws EntityNotFoundException, CommandRejectedException {
        rsOfferService.cancelRSOffer(new UserId(UUID.randomUUID()),dto.rsOfferId());
    }

    @POST
    @Path("/publish")
    public void publishRSOffer(PublishRSOfferDTO dto) throws EntityNotFoundException, CommandRejectedException {
        rsOfferService.publishRSOffer(new UserId(UUID.randomUUID()),dto.rsOfferId());  
    }

    @POST
    @Path("/close")
    public void closeRSOffer(CloseRSOfferDTO dto) throws EntityNotFoundException, CommandRejectedException {
        rsOfferService.closeRSOffer(dto.rsOfferId());
    }

    @POST
    @Path("/changeNumberOfAvailableSeats")
    public void changeNumberOfAvailableSeats(ChangeNumberOfAvailableSeatsDTO dto) throws EntityNotFoundException, CommandRejectedException {
        rsOfferService.changeNumberOfAvailableSeats(new UserId(UUID.randomUUID()),dto.rsOfferId(),dto.availableSeatsNumber());
    }

    @POST
    @Path("/changeDepartureTime")
    public void changeDepartureTime(ChangeDepartureTimeDTO dto) throws EntityNotFoundException, CommandRejectedException {
        rsOfferService.changeDepartureTime(new UserId(UUID.randomUUID()),dto.rsOfferId(),dto.departureDateTime());
    }

}
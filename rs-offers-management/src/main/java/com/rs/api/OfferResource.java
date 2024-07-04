package com.rs.api;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.rs.api.dtos.*;
import com.rs.application.services.RSOfferService;
import com.rs.domain.offer.UserId;
import com.rs.exceptions.CommandRejectedException;
import com.rs.exceptions.EntityNotFoundException;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

@Path("/offers")
public class OfferResource {

    @Inject
    private RSOfferService rsOfferService;

    @GET
    @RolesAllowed("admin")
    public List<RsOfferDto> findAll() {
        return rsOfferService.getAllRsOffers();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response findById(@RequestBody GetRsOfferByIdDto getRsOfferById) throws EntityNotFoundException {
        RsOfferDto rsOfferDto = rsOfferService.getOfferById(getRsOfferById.rsOfferId());
        if(Objects.nonNull(rsOfferDto)){
            return Response.accepted(rsOfferDto).build();
        }else{
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @POST
    @RolesAllowed("rider")
    public Response createRSOffer(@RequestBody CreateRSOfferDTO createRSOfferDTO) throws EntityNotFoundException{
        return Response.ok(rsOfferService.createRSOffer(createRSOfferDTO)).build();
    }
    
    @PUT
    @Path("/cancel")
    @RolesAllowed("rider")
    public Response cancelRSOffer(@RequestBody CancelRSOfferDTO cancelRSOfferDTO) throws EntityNotFoundException, CommandRejectedException {
        return Response.ok(rsOfferService.cancelRSOffer(cancelRSOfferDTO.rsOfferId())).build();
    }

    @PUT
    @Path("/publish")
    @RolesAllowed("rider")
    public void publishRSOffer(@RequestBody PublishRSOfferDTO dto) throws EntityNotFoundException, CommandRejectedException {
        rsOfferService.publishRSOffer(dto.rsOfferId());
    }

    @PUT
    @Path("/close")
    @RolesAllowed("rider")
    public void closeRSOffer(@RequestBody CloseRSOfferDTO dto) throws EntityNotFoundException, CommandRejectedException {
        rsOfferService.closeRSOffer(dto.rsOfferId());
    }

    @PUT
    @Path("/seats/available/change")
    @RolesAllowed("rider")
    public void changeNumberOfAvailableSeats(@RequestBody ChangeNumberOfAvailableSeatsDTO dto) throws EntityNotFoundException, CommandRejectedException {
        rsOfferService.changeNumberOfAvailableSeats(dto.rsOfferId(),dto.availableSeatsNumber());
    }

    @PUT
    @Path("/departure-time/change")
    @RolesAllowed("rider")
    public void changeDepartureTime(@RequestBody ChangeDepartureTimeDTO dto) throws EntityNotFoundException, CommandRejectedException {
        rsOfferService.changeDepartureTime(dto.rsOfferId(),dto.departureDateTime());
    }

}
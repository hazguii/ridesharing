package com.rs.api;

import com.rs.api.dtos.*;
import com.rs.application.services.RSDemandsService;
import com.rs.domain.demand.*;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.rs.domain.demand.RSDemandId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Path("/rsDemands")
public class RSDemandResource {

    @Inject
    RSDemandsService rsDemandsService;
    @Inject
    private RSDemandsRepository rsDemandsRepository;


    @GET
    @Path("/secretAdmin")
    @RolesAllowed("rs-admin")
    public String getSecretAdmin() {
        return "message que pour l'admin";
    }

    @GET
    public List<RSDemand> findAll(){
        return (rsDemandsRepository.findAll());
    }

    @GET
    @Path("/{rsDemandId}")
    public Response findById(@PathParam("rsDemandId") UUID id) {
        Optional<RSDemand> demand = rsDemandsRepository.lookup(new RSDemandId(id));
        if(demand.isPresent()){
            return Response.accepted(demand.get()).build();
        }else{
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @POST
    public RSDemandId createRSDemand(CreateRSDemandDTO dto){
        return rsDemandsService.createRSDemand(new UserId(UUID.randomUUID()),
                                              dto.departureAddress(),
                                              dto.departureGeoPoint(),
                                              dto.destinationAddress(),
                                              dto.destinationGeoPoint(),
                                              dto.departureDateTime());
    }

    @POST
    @Path("/cancel")
    public void cancelRSDemand(CancelRSDemandDTO dto){
        rsDemandsService.cancelRSDemand(dto.rsDemandId());
    }
    @POST
    @Path("/close")
    public void closeRSDemand(CloseRSDemandDTO dto){
        rsDemandsService.closeRSDemand(dto.rsDemandId());
    }
    @POST
    @Path("/publish")
    public void publishRSDemand(PublishRSDemandDTO dto){
        rsDemandsService.publishRSDemand(dto.rsDemandId());
    }
    @POST
    @Path("/changePickUpAddress")
    public void changeRSDemandPickUpAddress(ChangeRSDemandPickUpAddressDTO dto){
        rsDemandsService.changeRSDemandPickUpAddress(dto.rsDemandId(),dto.newRSPickUpAddress(), dto.newRSPickUpGeoPoint());
    }
    @POST
    @Path("/changeDepartureTime")
    public void changeRSDemandDepartureTime(ChangeRSDemandDepartureTimeDTO dto){
        rsDemandsService.changeRSDemandDepartureTime(dto.rsDemandId(),dto.newDepartureDateTime());

    }



}

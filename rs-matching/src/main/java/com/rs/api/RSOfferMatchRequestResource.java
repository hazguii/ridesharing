package com.rs.api;

import com.rs.application.RSOfferId;
import com.rs.application.services.RSMatchingApplicationService;
import com.rs.domain.RSOfferMatchRequest;
import com.rs.domain.RSOfferMatchRequestRepository;
import com.rs.domain.exceptions.EntityNotFoundException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("/rsOfferMatchRequest")
public class RSOfferMatchRequestResource {
    @Inject
    private RSOfferMatchRequestRepository repository;
    @Inject
    private RSMatchingApplicationService rsMatchingApplicationService;
    @GET
    public List<RSOfferMatchRequest> findAll(){
        return repository.findAll();
    }
    @GET
    @Path("/{offerUUID}")
    public RSOfferMatchRequest findById(@PathParam("offerUUID") UUID offerUUID){
        Optional<RSOfferMatchRequest> optional = repository.lookUp(new RSOfferId(offerUUID));
        if (optional.isPresent()){
            return optional.get();
        }else throw new EntityNotFoundException("No offer match request with id " + offerUUID);
    }

}

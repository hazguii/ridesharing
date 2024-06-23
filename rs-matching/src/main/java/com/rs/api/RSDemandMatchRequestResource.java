package com.rs.api;

import com.rs.application.RSDemandId;
import com.rs.application.services.RSMatchingApplicationService;
import com.rs.domain.RSDemandMatchRequest;
import com.rs.domain.RSDemandMatchRequestRepository;
import com.rs.domain.exceptions.EntityNotFoundException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("/rsDemandMatchRequests")
public class RSDemandMatchRequestResource {
    @Inject
    private RSDemandMatchRequestRepository repository;
    @Inject
    private RSMatchingApplicationService rsMatchingApplicationService;
    @GET
    public List<RSDemandMatchRequest> findAll(){
        return repository.findAll();
    }
    @GET
    @Path("/{demandUUID}")
    public RSDemandMatchRequest findById(@PathParam("demandUUID") UUID demandUUID){
        Optional<RSDemandMatchRequest> optional = repository.lookUp(new RSDemandId(demandUUID));
        if (optional.isPresent()){
            return optional.get();
        }else{
            throw new EntityNotFoundException("No demand match request with id " + demandUUID);
        }
    }

}

package com.rs.api;

import com.rs.application.RSDemandId;
import com.rs.application.services.RSMatchingApplicationService;
import com.rs.domain.RSProposal;
import com.rs.domain.RSProposalId;
import com.rs.domain.RSProposalRepository;
import com.rs.domain.exceptions.EntityNotFoundException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Path("/rsProposal")
public class RSProposalResource {
    @Inject
    private RSProposalRepository repository;
    @Inject
    private RSMatchingApplicationService rsMatchingApplicationService;
    @GET
    public List<RSProposal> findAll(){
        return repository.findAll();
    }
    @GET
    @Path("/findByDemandId/{demandUUID}")
    public List<RSProposal> findByDemandId(@PathParam("demandUUID") UUID demandUUID){
        return repository.findByDemandId(new RSDemandId(demandUUID));
    }

    @POST
    @Path("/{proposalUUID}/acceptProposal")
    public void acceptRSProposal(@PathParam("proposalUUID") UUID proposalUUID){
        rsMatchingApplicationService.acceptRSProposal(new RSProposalId(proposalUUID));
    }
    @POST
    @Path("/{proposalUUID}/rejectproposal")
    public void rejectProposal(@PathParam("proposalUUID") UUID proposalUUID){
        rsMatchingApplicationService.rejectRSProposal(new RSProposalId(proposalUUID));
    }
}

package com.rs.domain.services;

import com.rs.application.RSDemandId;
import com.rs.application.RSOfferId;
import com.rs.domain.*;
import com.rs.domain.events.RSMatchFound;
import com.rs.domain.events.RSProposalCreated;
import com.rs.domain.exceptions.EntityNotFoundException;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
@ApplicationScoped
public class RSMatchingDomainServiceImpl implements RSMatchingDomainService{
    @Inject
    private RSOfferMatchRequestRepository rsOfferMatchRequestRepository;
    @Inject
    private RSDemandMatchRequestRepository rsDemandMatchRequestRepository;
    @Inject
    private RSProposalRepository rsProposalRepository;
    @Inject
    @Channel("rsproposal-created")
    Emitter<RSProposalCreated> rsProposalCreatedEmitter;
    @Inject
    @Channel("rsmatch-found")
    Emitter<RSMatchFound> rsMatchFoundEmitter;
    @Override
    public RSProposalId createRSProposal(RSDemandId rsDemandId, RSOfferId rsOfferId) {
        RSProposal proposal = RSProposal.of(rsDemandId,rsOfferId);
        rsProposalRepository.save(proposal);
        rsProposalCreatedEmitter.send(new RSProposalCreated());
        return proposal.id();
    }

    @Override
    public void findMatchingDemands(RSOfferId rsOfferMatchRequestId) {
        Optional<RSOfferMatchRequest> optional = rsOfferMatchRequestRepository.lookUp(rsOfferMatchRequestId);
        if (optional.isPresent()) {
            RSOfferMatchRequest rsOfferMatchRequest = optional.get();
            List<RSDemandId> matchingDemands = rsDemandMatchRequestRepository.findMatchingDemands(rsOfferMatchRequest);
            for (RSDemandId demandId : matchingDemands){
                rsMatchFoundEmitter.send(new RSMatchFound(demandId, rsOfferMatchRequestId));
            }
        }else{
            throw new EntityNotFoundException("No offer match request with id " + rsOfferMatchRequestId.value().toString());
        }
    }

    @Override
    public void findMatchingOffers(RSDemandId rsDemandMatchRequestId) {
        Optional<RSDemandMatchRequest> optional = rsDemandMatchRequestRepository.lookUp(rsDemandMatchRequestId);
        if (optional.isPresent()){
            RSDemandMatchRequest rsDemandMatchRequest = optional.get();
            List<RSOfferId> matchingOffers = rsOfferMatchRequestRepository.findMatchingOffers(rsDemandMatchRequest);
            for (RSOfferId offerId : matchingOffers){
                rsMatchFoundEmitter.send(new RSMatchFound(rsDemandMatchRequestId, offerId));
            }
        }
    }
}

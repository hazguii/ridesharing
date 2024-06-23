package com.rs.application.services;

import com.rs.application.RSDemandId;
import com.rs.application.RSOfferId;
import com.rs.application.events.RSOfferDepartureTimeChanged;
import com.rs.application.events.RSProposalAccepted;
import com.rs.application.events.RSProposalRejected;
import com.rs.domain.*;
import com.rs.domain.events.*;
import com.rs.domain.exceptions.EntityNotFoundException;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@ApplicationScoped
public class RSMatchingApplicationServiceImpl implements RSMatchingApplicationService{
    @Inject
    RSDemandMatchRequestRepository rsDemandMatchRequestRepository;
    @Inject
    RSOfferMatchRequestRepository rsOfferMatchRequestRepository;
    @Inject
    RSProposalRepository rsProposalRepository;
    @Inject
    @Channel("rsdemandmatchrequest-stopped")
    Emitter<RSDemandMatchRequestStopped> rsDemandMatchRequestStoppedEmitter;
    @Inject
    @Channel("rsdemandmatchrequest-departuredatetimechanged")
    Emitter<RSDemandMatchRequestDepartureDateTimeChanged>  rsDemandMatchRequestDepartureDateTimeChangedEmitter;
    @Inject
    @Channel("rsdemandmatchrequest-departuregeopointchanged")
    Emitter<RSDemandMatchRequestDepartureGeoPointChanged> rsDemandMatchRequestDepartureGeoPointChangedEmitter;
    @Inject
    @Channel("rsdemandmatchrequest-created")
    Emitter<RSDemandMatchRequestCreated> rsDemandMatchRequestCreatedEmitter;
    @Inject
    @Channel("rsoffermatchrequest-created")
    Emitter<RSOfferMatchRequestCreated> rsOfferMatchRequestCreatedEmitter;
    @Inject
    @Channel("rsoffermatchrequest-stopped")
    Emitter<RSOfferMatchRequestStopped> rsOfferMatchRequestStoppedEmitter;
    @Inject
    @Channel("rsproposal-accepted")
    Emitter<RSProposalAccepted> rsProposalAcceptedEmitter;
    @Inject
    @Channel("rsproposal-rejected")
    Emitter<RSProposalRejected> rsProposalRejectedEmitter;
    @Inject
    @Channel("rsoffermatchrequest-departuredatetimechanged")
    Emitter<RSOfferMatchRequestDepartureTimeChanged> rsOfferMatchRequestDepartureTimeChangedEmitter;
    @Inject
    @Channel("rsproposal-cancelled")
    Emitter<RSProposalCancelled> RSProposalCancelledEmitter;

    @Override
    public void stopRSDemandMatchRequest(RSDemandId rsDemandMatchRequestId) {
        Optional<RSDemandMatchRequest> optional = rsDemandMatchRequestRepository.lookUp(rsDemandMatchRequestId);
        if (optional.isPresent()){
            RSDemandMatchRequest matchRequest = optional.get();
            RSDemandMatchRequestStopped event = matchRequest.stopMatching();
            rsDemandMatchRequestRepository.save(matchRequest);
            rsDemandMatchRequestStoppedEmitter.send(event);
        } else{
            throw new EntityNotFoundException("No demand match request with id " + rsDemandMatchRequestId.value().toString());
        }
    }

    @Override
    public void stopRSOfferMatchRequest(RSOfferId rsOfferMatchRequestId) {
        Optional<RSOfferMatchRequest> optional = rsOfferMatchRequestRepository.lookUp(rsOfferMatchRequestId);
        if (optional.isPresent()) {
            RSOfferMatchRequest matchRequest = optional.get();
            RSOfferMatchRequestStopped event = matchRequest.stopMatching();
            rsOfferMatchRequestRepository.save(matchRequest);
            rsOfferMatchRequestStoppedEmitter.send(event);
        }else{
            throw new IllegalStateException("No offer match request with id " + rsOfferMatchRequestId.value().toString());
        }
    }

    @Override
    public RSDemandId createRSDemandMatchingRequest(RSDemandId rsDemandId, GeoPoint departureGeoPoint, GeoPoint destinationGeoPoint, LocalDateTime departureDateTime) {
        RSDemandMatchRequest matchRequest = RSDemandMatchRequest.of(rsDemandId, departureGeoPoint, destinationGeoPoint, departureDateTime);
        rsDemandMatchRequestRepository.save(matchRequest);
        rsDemandMatchRequestCreatedEmitter.send(new RSDemandMatchRequestCreated(rsDemandId));
        return matchRequest.id();
    }

    @Override
    public RSOfferId createRSOfferMatchRequest(RSOfferId rsOfferId, GeoPoint departureGeoPoint, GeoPoint destinationGeoPoint, LocalDateTime departureDateTime) {
        RSOfferMatchRequest matchRequest = RSOfferMatchRequest.of(rsOfferId, departureGeoPoint, destinationGeoPoint, departureDateTime);
        rsOfferMatchRequestRepository.save(matchRequest);
        rsOfferMatchRequestCreatedEmitter.send(new RSOfferMatchRequestCreated(rsOfferId));
        return matchRequest.id();
    }

    @Override
    public void changeRSDemandDepartureTime(RSDemandId rsDemandMatchRequestId, LocalDateTime newDepartureDateTime) {
        Optional<RSDemandMatchRequest> optional = rsDemandMatchRequestRepository.lookUp(rsDemandMatchRequestId);
        if (optional.isPresent()){
            RSDemandMatchRequest matchRequest = optional.get();
            matchRequest.changeRequestDepartureDateTime(newDepartureDateTime);
            rsDemandMatchRequestRepository.save(matchRequest);
            rsDemandMatchRequestDepartureDateTimeChangedEmitter.send(new RSDemandMatchRequestDepartureDateTimeChanged(rsDemandMatchRequestId));
        }else{
            throw new EntityNotFoundException("No demand match request with id " + rsDemandMatchRequestId.value().toString());
        }
    }

    @Override
    public void changeRSDemandPickUpAddress(RSDemandId rsDemandMatchRequestId, GeoPoint newPickUpGeoPoint) {
        Optional<RSDemandMatchRequest> optional = rsDemandMatchRequestRepository.lookUp(rsDemandMatchRequestId);
        if (optional.isPresent()){
            RSDemandMatchRequest matchRequest = optional.get();
            matchRequest.changeRequestDepartureGeoPoint(newPickUpGeoPoint);
            rsDemandMatchRequestRepository.save(matchRequest);
            rsDemandMatchRequestDepartureGeoPointChangedEmitter.send(new RSDemandMatchRequestDepartureGeoPointChanged(rsDemandMatchRequestId));
        }else{
            throw new EntityNotFoundException("No demand match request with id " + rsDemandMatchRequestId.value().toString());
        }
    }

    @Override
    public void acceptRSProposal(RSProposalId rsProposalId) {
        Optional<RSProposal> optional = rsProposalRepository.lookUp(rsProposalId);
        if (optional.isPresent()){
            RSProposal rsProposal = optional.get();
            RSProposalAccepted event = rsProposal.accept();
            rsProposalRepository.save(rsProposal);
            rsProposalAcceptedEmitter.send(event);
        }else{
            throw new EntityNotFoundException("no proposal with id " + rsProposalId.value().toString());
        }
    }

    @Override
    public void rejectRSProposal(RSProposalId rsProposalId) {
        Optional<RSProposal> optional = rsProposalRepository.lookUp(rsProposalId);
        if (optional.isPresent()){
            RSProposal rsProposal = optional.get();
            RSProposalRejected event = rsProposal.reject();
            rsProposalRepository.save(rsProposal);
            rsProposalRejectedEmitter.send(event);
        }else{
            throw new EntityNotFoundException("no proposal with id " + rsProposalId.value().toString());
        }
    }

    @Override
    public void changeRSOfferDepartureTime(RSOfferId rsOfferId, LocalDateTime newDepartureDateTime) {
        Optional<RSOfferMatchRequest> optional = rsOfferMatchRequestRepository.lookUp(rsOfferId);
        if(optional.isPresent()){
            RSOfferMatchRequest matchRequest = optional.get();
            RSOfferMatchRequestDepartureTimeChanged event = matchRequest.changeDepartureDateTime(newDepartureDateTime);
            rsOfferMatchRequestRepository.save(matchRequest);
            rsOfferMatchRequestDepartureTimeChangedEmitter.send(event);
        }else{
            throw new EntityNotFoundException("no offer match request found with id " + rsOfferId.value().toString());
        }
    }

    @Override
    public void cancelProposalsByDemand(RSDemandId rsDemandId) {
        List<RSProposal> proposalsList = rsProposalRepository.findByDemandId(rsDemandId);
        for (RSProposal p : proposalsList){
            RSProposalCancelled event = p.cancel();
            RSProposalCancelledEmitter.send(event);
        }
    }

    @Override
    public void cancelProposalsByOffer(RSOfferId rsOfferId) {
        List<RSProposal> proposalsList = rsProposalRepository.findByOfferId(rsOfferId);
        for (RSProposal p : proposalsList){
            RSProposalCancelled event = p.cancel();
            RSProposalCancelledEmitter.send(event);
        }
    }
}

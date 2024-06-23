package com.rs.application.services;

import com.rs.application.RSDemandId;
import com.rs.application.RSOfferId;
import com.rs.domain.GeoPoint;
import com.rs.domain.RSProposalId;

import java.time.LocalDateTime;

public interface RSMatchingApplicationService{
    void stopRSDemandMatchRequest(RSDemandId rsDemandMatchRequestId);
    void stopRSOfferMatchRequest(RSOfferId rsOfferMatchRequestId);

    RSDemandId createRSDemandMatchingRequest(RSDemandId rsDemandId, GeoPoint departureGeoPoint, GeoPoint destinationGeoPoint, LocalDateTime departureDateTime);
    RSOfferId createRSOfferMatchRequest(RSOfferId rsOfferId, GeoPoint departureGeoPoint, GeoPoint destinationGeoPoint, LocalDateTime departureDateTime);
    void changeRSDemandDepartureTime(RSDemandId rsDemandMatchRequestId, LocalDateTime newDepartureDateTime);
    void changeRSDemandPickUpAddress(RSDemandId rsDemandMatchRequestId, GeoPoint newPickUpGeoPoint);
    void acceptRSProposal(RSProposalId rsProposalId);
    void rejectRSProposal(RSProposalId rsProposalId);
    void changeRSOfferDepartureTime(RSOfferId rsOfferId, LocalDateTime newDepartureDateTime);
    void cancelProposalsByDemand(RSDemandId rsDemandId);
    void cancelProposalsByOffer(RSOfferId rsOfferId);
}


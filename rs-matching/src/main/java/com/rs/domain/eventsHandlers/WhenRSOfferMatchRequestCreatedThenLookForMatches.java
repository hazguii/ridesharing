package com.rs.domain.eventsHandlers;

import com.ddd.EventHandler;
import com.rs.domain.events.RSOfferMatchRequestCreated;
import com.rs.domain.services.RSMatchingDomainService;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.inject.Inject;

public class WhenRSOfferMatchRequestCreatedThenLookForMatches implements EventHandler<RSOfferMatchRequestCreated> {
    @Inject
    RSMatchingDomainService rsMatchingDomainService;
    @Override
    @Incoming("incoming-rsoffermatchrequest-created")
    public void handle(RSOfferMatchRequestCreated event) {
        rsMatchingDomainService.findMatchingDemands(event.rsOfferMatchRequestId());
    }
}

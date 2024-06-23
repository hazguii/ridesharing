package com.rs.domain.eventsHandlers;

import com.ddd.EventHandler;
import com.rs.domain.events.RSDemandMatchRequestCreated;
import com.rs.domain.services.RSMatchingDomainService;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.inject.Inject;

public class WhenRSDemandMatchRequestCreatedThenLookForMatches implements EventHandler<RSDemandMatchRequestCreated> {
    @Inject
    RSMatchingDomainService rsMatchingDomainService;
    @Override
    @Incoming("incoming-rsdemandmatchrequest-created")
    public void handle(RSDemandMatchRequestCreated event) {
        rsMatchingDomainService.findMatchingOffers(event.rsDemandMatchRequestId());
    }
}

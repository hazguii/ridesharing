package com.rs.domain.eventsHandlers;

import com.ddd.EventHandler;
import com.rs.domain.events.RSMatchFound;
import com.rs.domain.services.RSMatchingDomainService;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.inject.Inject;

public class WhenRSMatchFoundThenCreateRSProposal implements EventHandler<RSMatchFound> {
    @Inject
    RSMatchingDomainService rsMatchingDomainService;

    @Override
    @Incoming("incoming-rsmatch-found")
    public void handle(RSMatchFound event) {
        rsMatchingDomainService.createRSProposal(event.rsDemandId(),event.rsOfferId());
    }
}

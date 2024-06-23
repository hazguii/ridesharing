package com.rs.application.eventsHandlers;

import com.ddd.EventHandler;
import com.rs.application.events.RSOfferCancelled;
import com.rs.application.services.RSMatchingApplicationService;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.inject.Inject;

public class WhenRSOfferCancelledThenCancelItsAssociatedProposals implements EventHandler<RSOfferCancelled> {
    @Inject
    RSMatchingApplicationService rsMatchingApplicationService;

    @Incoming("incoming-rsoffer-cancelled")
    public void handle(RSOfferCancelled event) {
        rsMatchingApplicationService.cancelProposalsByOffer(event.rsOfferId());
    }
}

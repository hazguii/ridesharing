package com.rs.application.eventsHandlers;

import com.ddd.EventHandler;
import com.rs.application.events.RSDemandCancelled;
import com.rs.application.services.RSMatchingApplicationService;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.inject.Inject;

public class WhenRSDemandCancelledThenCancelItsAssociatedProposals implements EventHandler<RSDemandCancelled> {

    @Inject
    RSMatchingApplicationService rsMatchingApplicationService;

    @Override
    @Incoming("incoming-rsdemand-cancelled")
    public void handle(RSDemandCancelled event) {
        rsMatchingApplicationService.cancelProposalsByDemand(event.rsDemandId());
    }
}

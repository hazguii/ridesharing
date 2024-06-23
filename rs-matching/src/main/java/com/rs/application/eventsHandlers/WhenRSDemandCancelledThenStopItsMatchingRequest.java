package com.rs.application.eventsHandlers;

import com.ddd.EventHandler;
import com.rs.application.events.RSDemandCancelled;
import com.rs.application.services.RSMatchingApplicationService;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.inject.Inject;

public class WhenRSDemandCancelledThenStopItsMatchingRequest implements EventHandler<RSDemandCancelled> {
    @Inject
    RSMatchingApplicationService rsMatchingApplicationService;
    @Incoming("incoming-rsdemand-cancelled")
    public void handle(RSDemandCancelled event) {
        rsMatchingApplicationService.stopRSDemandMatchRequest(event.rsDemandId());
    }
}
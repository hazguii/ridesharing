package com.rs.application.eventsHandlers;

import com.ddd.EventHandler;
import com.rs.application.events.RSDemandClosed;
import com.rs.application.services.RSMatchingApplicationService;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.inject.Inject;

public class WhenRSDemandClosedThenStopItsMatchRequest implements EventHandler<RSDemandClosed> {
    @Inject
    RSMatchingApplicationService rsMatchingApplicationService;
    @Incoming("incoming-rsdemand-closed")
    public void handle(RSDemandClosed event) {
        rsMatchingApplicationService.stopRSDemandMatchRequest(event.rsDemandId());
    }
}

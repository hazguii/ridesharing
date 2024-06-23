package com.rs.application.eventsHandlers;

import com.ddd.EventHandler;
import com.rs.application.events.RSOfferClosed;
import com.rs.application.services.RSMatchingApplicationService;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.inject.Inject;

public class WhenRSOfferClosedThenStopItsMatchRequest implements EventHandler<RSOfferClosed> {
    @Inject
    private RSMatchingApplicationService rsMatchingApplicationService;
    @Incoming("incoming-rsoffer-closed")
    public void handle(RSOfferClosed event) {
        rsMatchingApplicationService.stopRSOfferMatchRequest(event.rsOfferId());
    }
}

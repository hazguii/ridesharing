package com.rs.application.eventsHandlers;

import com.ddd.EventHandler;
import com.rs.application.events.RSDemandPublished;
import com.rs.application.services.RSMatchingApplicationService;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.inject.Inject;

public class WhenRSDemandPublishedThenCreateRSDemandMatchingRequest implements EventHandler<RSDemandPublished> {
    @Inject
    RSMatchingApplicationService rsMatchingApplicationService;
    @Incoming("incoming-rsdemand-published")
    @Override
    public void handle(RSDemandPublished event) {
        rsMatchingApplicationService.createRSDemandMatchingRequest(event.rsDemandId(), event.departureGeoPoint(), event.destinationGeoPoint(), event.departureDateTime());
    }
}
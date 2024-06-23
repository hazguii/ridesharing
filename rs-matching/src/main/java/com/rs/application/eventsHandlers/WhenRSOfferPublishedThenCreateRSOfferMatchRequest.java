package com.rs.application.eventsHandlers;

import com.ddd.EventHandler;
import com.rs.application.events.RSOfferPublished;
import com.rs.application.services.RSMatchingApplicationService;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.inject.Inject;

public class WhenRSOfferPublishedThenCreateRSOfferMatchRequest implements EventHandler<RSOfferPublished> {
    @Inject
    RSMatchingApplicationService rsMatchingApplicationService;
    @Incoming("incoming-rsoffer-published")
    @Override
    public void handle(RSOfferPublished event) {
        rsMatchingApplicationService.createRSOfferMatchRequest(event.rsOfferId(), event.departureGeoPoint(), event.destinationGeoPoint(), event.departureDateTime());
    }
}
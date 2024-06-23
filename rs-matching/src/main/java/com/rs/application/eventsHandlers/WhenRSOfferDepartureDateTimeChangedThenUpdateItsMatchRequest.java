package com.rs.application.eventsHandlers;

import com.ddd.EventHandler;
import com.rs.application.events.RSOfferDepartureTimeChanged;
import com.rs.application.services.RSMatchingApplicationService;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.inject.Inject;

public class WhenRSOfferDepartureDateTimeChangedThenUpdateItsMatchRequest implements EventHandler<RSOfferDepartureTimeChanged> {
    @Inject
    RSMatchingApplicationService rsMatchingApplicationService;

    @Override
    @Incoming("incoming-rsoffer-departuretimechanged")
    public void handle(RSOfferDepartureTimeChanged event) {
        rsMatchingApplicationService.changeRSOfferDepartureTime(event.rsOfferId(), event.newDepartureDateTime());
    }
}

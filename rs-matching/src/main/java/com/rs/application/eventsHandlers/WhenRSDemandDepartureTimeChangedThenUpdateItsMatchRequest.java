package com.rs.application.eventsHandlers;

import com.ddd.EventHandler;
import com.rs.application.events.RSDemandDepartureTimeChanged;
import com.rs.application.services.RSMatchingApplicationService;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.inject.Inject;

public class WhenRSDemandDepartureTimeChangedThenUpdateItsMatchRequest implements EventHandler<RSDemandDepartureTimeChanged> {
    @Inject
    RSMatchingApplicationService rsMatchingApplicationService;
    @Incoming("incoming-rsdemand-departure-time-changed")
    public void handle(RSDemandDepartureTimeChanged event) {
        rsMatchingApplicationService.changeRSDemandDepartureTime(event.rsDemandId(), event.newDepartureDateTime());
    }
}

package com.rs.application.eventsHandlers;

import com.ddd.EventHandler;
import com.rs.application.events.RSDemandPickUpAddressChanged;
import com.rs.application.services.RSMatchingApplicationService;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.inject.Inject;

public class WhenRSDemandPickUpAddressChangedThenUpdateItsMatchRequest implements EventHandler<RSDemandPickUpAddressChanged> {
    @Inject
    RSMatchingApplicationService rsMatchingApplicationService;
    @Incoming("incoming-rsdemand-pickup-address-changed")
    public void handle(RSDemandPickUpAddressChanged event) {
        rsMatchingApplicationService.changeRSDemandPickUpAddress(event.rsDemandId(), event.newGeoPoint());
    }
}

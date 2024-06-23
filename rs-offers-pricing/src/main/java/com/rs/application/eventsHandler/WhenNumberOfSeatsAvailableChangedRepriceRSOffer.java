package com.rs.application.eventsHandler;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import com.ddd.EventHandler;
import com.rs.domain.pricing.events.DepartureTimeChanged;
import com.rs.domain.services.PricingService;
public class WhenNumberOfSeatsAvailableChangedRepriceRSOffer implements EventHandler<DepartureTimeChanged>{

    @Inject
    PricingService pricingService;

    @Incoming("numberofavailableseats-changed")
    @Override
    public void handle(DepartureTimeChanged event) {
        pricingService.price(event.rsOfferId());
    }

    
}

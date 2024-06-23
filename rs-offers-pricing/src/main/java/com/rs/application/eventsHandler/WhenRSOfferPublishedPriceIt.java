package com.rs.application.eventsHandler;

import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import com.ddd.EventHandler;
import com.rs.domain.pricing.events.RSOfferPublished;
import com.rs.domain.services.PricingService;

public class WhenRSOfferPublishedPriceIt implements EventHandler<RSOfferPublished>{

    @Inject
    PricingService pricingService;

    @Incoming("rsoffer-published")
    @Override
    public void handle(RSOfferPublished event) {
        pricingService.price(event.rsOfferId());
    }

    
}



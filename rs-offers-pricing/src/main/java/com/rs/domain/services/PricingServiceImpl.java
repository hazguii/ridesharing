package com.rs.domain.services;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import com.rs.domain.pricing.RSOfferId;
import com.rs.domain.pricing.RSOfferPricing;
import com.rs.domain.pricing.RSOfferPricingRepository;
import com.rs.domain.pricing.events.RSOfferPriceChanged;
import com.rs.domain.pricing.events.RSOfferPriced;

@ApplicationScoped
public class PricingServiceImpl implements PricingService {
    @Inject
    RSOfferPricingRepository rsOfferPricingRepository;

    @Inject
    @Channel("rsoffer-priced")
    Emitter<RSOfferPriced> rsOfferPricedEmitter;

    @Inject
    @Channel("rsoffer-price-changed")
    Emitter<RSOfferPriceChanged> rsOfferPriceChangedEmitter;

    @Override
    public void price(RSOfferId rsOfferId) {
        Optional<RSOfferPricing> oldPricing = rsOfferPricingRepository.lookup(rsOfferId);
        if(oldPricing.isPresent()){
            RSOfferPriceChanged ev = oldPricing.get().updatePrice(100*Math.random());
            rsOfferPricingRepository.save(oldPricing.get());
            rsOfferPriceChangedEmitter.send(ev); 
        }else{
            RSOfferPricing rsOfferPricing = RSOfferPricing.of(rsOfferId,100.0d);
            RSOfferPriced event = new RSOfferPriced(rsOfferPricing.getRsOfferId(),rsOfferPricing.getPrice());
            rsOfferPricingRepository.save(rsOfferPricing);
            rsOfferPricedEmitter.send(event);  
        }
         
    }
    
}

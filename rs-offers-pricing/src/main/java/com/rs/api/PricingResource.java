package com.rs.api;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.rs.domain.pricing.RSOfferId;
import com.rs.domain.pricing.RSOfferPricing;
import com.rs.domain.pricing.RSOfferPricingRepository;
import com.rs.domain.services.PricingService;

@Path("/price")
public class PricingResource {
    @Inject 
    PricingService pricingService;
    @Inject
    RSOfferPricingRepository rsOffersPricingsRepo;
    
    @GET
    public List<RSOfferPricing> findAll() {
        return rsOffersPricingsRepo.findAll();
    }

    @GET
    @Path("/{rsOfferId}")
    public Response findById(@PathParam("rsOfferId") UUID id) {
        Optional<RSOfferPricing> offerPricing = rsOffersPricingsRepo.lookup(new RSOfferId(id));
        if(offerPricing.isPresent()){
            return Response.accepted(offerPricing.get()).build();
        }else{
            return Response.status(Status.NOT_FOUND).build();
        }
    }


}

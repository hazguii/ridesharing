package com.rs.domain.pricing;

import java.util.List;

import com.ddd.Repository;

public interface RSOfferPricingRepository extends Repository<RSOfferPricing,RSOfferId>{
    List<RSOfferPricing> findAll();
    List<RSOfferPricing> findAllUnclosedOffers();
}

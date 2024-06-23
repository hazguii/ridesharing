package com.rs.domain.pricing;

import com.ddd.AggregateRoot;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.rs.domain.pricing.events.*;

import lombok.Getter;

@Getter
public class RSOfferPricing implements AggregateRoot<RSOfferId>{
    private RSOfferId rsOfferId;
    private double price = 0;


    @JsonCreator
    public static RSOfferPricing of(RSOfferId RSOfferId,double price){
        return new RSOfferPricing(RSOfferId,price);
    }
    
    private RSOfferPricing(RSOfferId rsOfferId, double price) {
        this.rsOfferId = rsOfferId;
        this.price = price;
    }

    @Override
    public RSOfferId id() {
        return this.rsOfferId;
    }
    public RSOfferPriceChanged updatePrice(double price){
            this.price = price;    
            return new RSOfferPriceChanged(this.rsOfferId,this.price);
        
    }

    }
 

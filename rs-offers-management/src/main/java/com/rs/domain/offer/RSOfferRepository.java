package com.rs.domain.offer;

import java.util.List;

import com.ddd.Repository;

public interface RSOfferRepository extends Repository<RSOffer,RSOfferId>{
    List<RSOffer> findAll();
    List<RSOffer> findAllUnclosedOffers();
}

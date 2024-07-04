package com.rs.domain.offer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ddd.Repository;

public interface RSOfferRepository extends Repository<RSOffer,RSOfferId>{
    List<RSOffer> findAll();
    List<RSOffer> findAllUnclosedOffers();
    Optional<RSOffer> findById(UUID id);
}

package com.rs.domain;

import com.ddd.Repository;
import com.rs.application.RSOfferId;

import java.util.List;

public interface RSOfferMatchRequestRepository extends Repository<RSOfferMatchRequest,RSOfferId> {
    List<RSOfferId> findMatchingOffers(RSDemandMatchRequest rsDemandMatchRequest);
    List<RSOfferMatchRequest> findAll();
}

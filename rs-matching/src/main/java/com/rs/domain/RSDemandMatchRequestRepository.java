package com.rs.domain;

import com.ddd.Repository;
import com.rs.application.RSDemandId;

import java.util.List;

public interface RSDemandMatchRequestRepository extends Repository<RSDemandMatchRequest, RSDemandId> {
    List<RSDemandId> findMatchingDemands(RSOfferMatchRequest rsOfferMatchRequest);
    List<RSDemandMatchRequest> findAll();
}

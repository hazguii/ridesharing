package com.rs.domain.services;

import com.rs.application.RSDemandId;
import com.rs.application.RSOfferId;
import com.rs.domain.*;

import java.time.LocalDateTime;
import java.util.List;

public interface RSMatchingDomainService {
    RSProposalId createRSProposal(RSDemandId rsDemandId, RSOfferId rsOfferId);
    void findMatchingDemands(RSOfferId rsOfferId);
    void findMatchingOffers(RSDemandId rsDemandId);

}

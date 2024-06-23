package com.rs.domain;

import com.ddd.Repository;
import com.rs.application.RSDemandId;
import com.rs.application.RSOfferId;

import java.util.List;
import java.util.Optional;

public interface RSProposalRepository extends Repository<RSProposal, RSProposalId> {
    Optional<RSProposal> lookUp(RSProposalId rsProposalId);
    List<RSProposal> findAll();
    List<RSProposal> findByDemandId(RSDemandId rsDemandId);
    List<RSProposal> findByOfferId(RSOfferId rsOfferId);
    List<RSProposal> findAcceptedProposalsByDemandId(RSDemandId rsDemandId);
}

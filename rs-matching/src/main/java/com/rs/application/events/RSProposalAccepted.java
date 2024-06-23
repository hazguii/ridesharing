package com.rs.application.events;

import com.ddd.DomainEvent;
import com.rs.domain.RSProposal;
import com.rs.domain.RSProposalId;

import java.time.LocalDateTime;
import java.util.UUID;

public record RSProposalAccepted(UUID eventId, LocalDateTime eventTime, RSProposalId rsProposalId) implements DomainEvent{
    public RSProposalAccepted(RSProposalId rsProposalId){
        this(UUID.randomUUID(), LocalDateTime.now(), rsProposalId);
    }
    @Override
    public String aggregateId() {
        return rsProposalId.value().toString();
    }

    @Override
    public String aggregateType() {
        return "RSProposal";
    }

    @Override
    public String eventType() {
        return "RSProposalAccepted";
    }
}

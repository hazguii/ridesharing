package com.rs.domain.events;

import com.ddd.DomainEvent;
import com.rs.domain.RSProposal;
import com.rs.domain.RSProposalId;

import java.time.LocalDateTime;
import java.util.UUID;

public record RSProposalCreated(UUID eventId, LocalDateTime eventTime, RSProposalId rsProposalId) implements DomainEvent {
    public RSProposalCreated(){
        this(UUID.randomUUID(), LocalDateTime.now(), new RSProposalId());
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
        return "RSProposalCreated";
    }
}

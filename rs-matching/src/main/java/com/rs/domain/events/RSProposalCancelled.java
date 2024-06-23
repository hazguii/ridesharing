package com.rs.domain.events;

import com.ddd.DomainEvent;
import com.rs.domain.RSProposalId;

import java.time.LocalDateTime;
import java.util.UUID;

public record RSProposalCancelled(UUID eventId, LocalDateTime eventTime, RSProposalId rsProposalId) implements DomainEvent {
    public RSProposalCancelled(RSProposalId rsProposalId){
        this(UUID.randomUUID(), LocalDateTime.now(), rsProposalId);
    }
    @Override
    public String aggregateId() {
        return this.rsProposalId.value().toString();
    }

    @Override
    public String aggregateType() {
        return "RSProposal";
    }

    @Override
    public String eventType() {
        return "RSProposalCancelled";
    }
}

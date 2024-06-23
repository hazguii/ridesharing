package com.rs.domain.events;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ddd.DomainEvent;
import com.rs.domain.EmailId;

public record EmailSent (UUID eventId, EmailId emailId)implements DomainEvent{
    public EmailSent(EmailId emailId) {
        this(UUID.randomUUID(),emailId);
    }

    @Override
    public String aggregateId() {
        return emailId.toString();
    }
    @Override
    public String aggregateType() {
        return "Email";
    }
    @Override
    public String eventType() {
        return "EmailCreated";
    }

    @Override
    public LocalDateTime eventTime() {
        // TODO Auto-generated method stub
        return null;
    }
}

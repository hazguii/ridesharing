package com.ddd;

import java.time.LocalDateTime;
import java.util.UUID;

public interface DomainEvent {
    UUID eventId();
    String aggregateId();
    String aggregateType();
    LocalDateTime eventTime();
    String eventType();
}

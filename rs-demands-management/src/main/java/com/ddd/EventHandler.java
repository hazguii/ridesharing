package com.ddd;

public interface EventHandler<E extends DomainEvent> {
    void handle(E event);
}

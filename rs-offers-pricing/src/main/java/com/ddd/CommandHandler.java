package com.ddd;

public interface CommandHandler<C extends Command> {
    void handle(C command);
}

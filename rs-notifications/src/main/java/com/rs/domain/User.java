package com.rs.domain;

import java.util.UUID;

public record User(UserId userId, String nom, String prenom, String emailAddress) {

    public User(String nom, String prenom, String emailAddress){
        this(new UserId(UUID.randomUUID()),nom,prenom,emailAddress);
    }
    
}

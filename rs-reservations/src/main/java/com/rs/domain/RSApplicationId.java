package com.rs.domain;

import java.util.UUID;

public record RSApplicationId(UUID id) {
    public String stringValue(){
        return id.toString();
    }
}

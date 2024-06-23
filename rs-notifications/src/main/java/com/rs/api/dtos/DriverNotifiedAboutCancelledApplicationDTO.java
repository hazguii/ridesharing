package com.rs.api.dtos;

import com.rs.domain.RSApplicationId;
import com.rs.domain.User;

public record DriverNotifiedAboutCancelledApplicationDTO (User driver, RSApplicationId rsApplicationId) {
    
}

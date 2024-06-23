
package com.rs.api.dtos;

import com.rs.domain.RSApplicationId;
import com.rs.domain.TripId;

public record RejectRSApplicationDTO(TripId tripId,RSApplicationId applicationId) {
    
}
    

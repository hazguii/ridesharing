package com.rs.api.dtos;

import java.time.LocalDateTime;
import com.rs.domain.demand.RSDemand;
import com.rs.domain.demand.RSDemandId;

public record ChangeRSDemandDepartureTimeDTO(RSDemandId rsDemandId, LocalDateTime newDepartureDateTime){

}

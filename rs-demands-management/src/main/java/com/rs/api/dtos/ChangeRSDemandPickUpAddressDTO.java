package com.rs.api.dtos;

import com.rs.domain.demand.GeoPoint;
import com.rs.domain.demand.RSDemandId;
import com.rs.domain.demand.Address;

public record ChangeRSDemandPickUpAddressDTO(RSDemandId rsDemandId, Address newRSPickUpAddress, GeoPoint newRSPickUpGeoPoint) {
}

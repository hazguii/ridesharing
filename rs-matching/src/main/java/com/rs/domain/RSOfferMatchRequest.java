package com.rs.domain;

import com.ddd.AggregateRoot;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.rs.application.RSOfferId;
import com.rs.domain.events.RSOfferMatchRequestCreated;
import com.rs.domain.events.RSOfferMatchRequestDepartureTimeChanged;
import com.rs.domain.events.RSOfferMatchRequestStopped;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class RSOfferMatchRequest implements AggregateRoot<RSOfferId> {
    private RSOfferId rsOfferId;
    private GeoPoint departureGeoPoint;
    private GeoPoint destinationGeoPoint;
    private LocalDateTime departureDateTime;
    private Boolean isMatching;
    @JsonCreator
    public static RSOfferMatchRequest of(RSOfferId rsOfferId, GeoPoint departureGeoPoint, GeoPoint destinationGeoPoint, LocalDateTime departureDateTime){
        if (rsOfferId == null) throw new IllegalArgumentException("rsOffedId cannot be null");
        return new RSOfferMatchRequest(rsOfferId, departureGeoPoint, destinationGeoPoint, departureDateTime, true);
    }


    private RSOfferMatchRequest(RSOfferId rsOfferId, GeoPoint departureGeoPoint, GeoPoint destinationGeoPoint, LocalDateTime departureDateTime, boolean isMatching){
        this.rsOfferId = rsOfferId;
        this.departureDateTime = departureDateTime;
        this.departureGeoPoint = departureGeoPoint;
        this.destinationGeoPoint = destinationGeoPoint;
        this.isMatching = isMatching;
    }
    @Override
    public RSOfferId id() {
        return rsOfferId;
    }
    public RSOfferMatchRequestStopped stopMatching(){
        if (this.isMatching){
            this.isMatching = false;
            return new RSOfferMatchRequestStopped(this.rsOfferId);
        }else{
            throw new IllegalStateException("Offer match request with id " + this.rsOfferId.value().toString() + " is already not matching");
        }
    }
    public RSOfferMatchRequestDepartureTimeChanged changeDepartureDateTime(LocalDateTime newDepartureDateTime){
        if (newDepartureDateTime.isAfter(LocalDateTime.now())){
            this.departureDateTime = newDepartureDateTime;
            return new RSOfferMatchRequestDepartureTimeChanged(this.id());
        }else{
            throw new IllegalArgumentException("New departure date time invalid");
        }
    }
}

package com.rs.domain;

import com.ddd.AggregateRoot;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.rs.application.RSDemandId;
import com.rs.domain.events.RSDemandMatchRequestStopped;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class RSDemandMatchRequest implements AggregateRoot<RSDemandId> {
    private RSDemandId rsDemandId;
    private GeoPoint departureGeoPoint;
    private GeoPoint destinationGeoPoint;
    private LocalDateTime departureDateTime;
    private Boolean isMatching;
    public RSDemandId id() {
        return rsDemandId;
    }

    @JsonCreator
    public static RSDemandMatchRequest of(RSDemandId rsDemandId, GeoPoint departureGeoPoint, GeoPoint destinationGeoPoint, LocalDateTime departureDateTime){
        if (rsDemandId == null) throw new IllegalArgumentException("rsDemandId cannot be null");
        return new RSDemandMatchRequest(rsDemandId, departureGeoPoint, destinationGeoPoint, departureDateTime, true);
    }
    private RSDemandMatchRequest(RSDemandId rsDemandId, GeoPoint departureGeoPoint, GeoPoint destinationGeoPoint, LocalDateTime departureDateTime, boolean isMatching){
        this.rsDemandId = rsDemandId;
        this.departureGeoPoint = departureGeoPoint;
        this.destinationGeoPoint = destinationGeoPoint;
        this.departureDateTime = departureDateTime;
        this.isMatching = isMatching;
    }
    private RSDemandMatchRequest(RSDemandId rsDemandId, GeoPoint departureGeoPoint, GeoPoint destinationGeoPoint, LocalDateTime departureDateTime){
        this.rsDemandId = rsDemandId;
        this.departureGeoPoint = departureGeoPoint;
        this.destinationGeoPoint = destinationGeoPoint;
        this.departureDateTime = departureDateTime;
        this.isMatching = true;
    }
    public void changeRequestDepartureDateTime(LocalDateTime newDepartureDateTime){
        if(newDepartureDateTime.isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("New Departure date time cannot be in the past");
        }else{
            this.departureDateTime = newDepartureDateTime;
        }
    }

    public RSDemandMatchRequestStopped stopMatching(){
        if (this.isMatching){
            this.isMatching = false;
            return new RSDemandMatchRequestStopped(this.rsDemandId);
        }else{
            throw new IllegalStateException("Match request " + this.rsDemandId + " is already not matching");
        }
    }

    public void changeRequestDepartureGeoPoint(GeoPoint newDepartureGeoPoint){
        if(this.departureGeoPoint != newDepartureGeoPoint){
            this.departureGeoPoint = newDepartureGeoPoint;
        }else{
            throw new IllegalArgumentException("New departure geo point cannot be the same as the actual departure geo point");
        }
    }
}

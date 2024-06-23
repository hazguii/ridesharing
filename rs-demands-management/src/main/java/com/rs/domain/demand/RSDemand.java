package com.rs.domain.demand;

import com.ddd.AggregateRoot;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.rs.domain.demand.events.*;
import io.quarkus.scheduler.Scheduled;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class RSDemand implements AggregateRoot<RSDemandId> {

    private RSDemandId rsDemandId;
    private UserId userId;
    private Address departureAddress;
    private GeoPoint departureGeoPoint;
    private Address destinationAddress;
    private GeoPoint destinationGeoPoint;
    private LocalDateTime departureDateTime;
    private boolean closed = false;
    private boolean cancelled = false;
    private boolean published = false;
    @Override
    public RSDemandId id() {
        return this.rsDemandId;
    }

    private RSDemand(RSDemandId rsDemandId, UserId userId, Address departureAddress, GeoPoint departureGeoPoint, Address destinationAddress,
                     GeoPoint destinationGeoPoint, LocalDateTime departureDateTime, boolean closed, boolean cancelled, boolean published) {
        this.rsDemandId = rsDemandId;
        this.userId = userId;
        this.departureAddress = departureAddress;
        this.departureGeoPoint = departureGeoPoint;
        this.destinationAddress = destinationAddress;
        this.destinationGeoPoint = destinationGeoPoint;
        this.departureDateTime = departureDateTime;
        this.closed = closed;
        this.cancelled = cancelled;
        this.published = published;
    }
    @JsonCreator
    public static RSDemand of(UserId userId, Address departureAddress,GeoPoint departureGeoPoint, Address destinationAddress, GeoPoint destinationGeoPoint, LocalDateTime departureDateTime){
        if (userId == null){
            throw new IllegalArgumentException("Cannot create a demand without owner");
        } return new RSDemand(new RSDemandId(), userId, departureAddress, departureGeoPoint, destinationAddress, destinationGeoPoint,  departureDateTime, false, false, false);
    }
    private RSDemand(UserId userId, Address departureAddress, GeoPoint departureGeoPoint, Address destinationAddress, GeoPoint destinationGeoPoint, LocalDateTime departureDateTime){
        this.userId = userId;
        this.departureAddress = departureAddress;
        this.departureGeoPoint = departureGeoPoint;
        this.destinationAddress = destinationAddress;
        this.destinationGeoPoint = destinationGeoPoint;
        this.departureDateTime = departureDateTime;
    }

    public RSDemandCancelled cancel(){
        if (!cancelled) {
            this.cancelled = true;
            return new RSDemandCancelled(this.rsDemandId);
        }
        throw new IllegalStateException("rsDemand is already cancelled");
    }
    @Scheduled(every = "3600s")
    public RSDemandClosed close(){
        if (!closed) {
            this.closed = true;
            return new RSDemandClosed(this.rsDemandId);
        }
        throw new IllegalStateException("rsDemand is already closed");

    }
    public RSDemandPublished publish(){
        if (!published) {
            this.published = true;
            return new RSDemandPublished(this.rsDemandId, userId, departureGeoPoint, destinationGeoPoint, departureDateTime);
        }
        throw new IllegalStateException("rsDemand is already published");
    }
    public RSDemandDepartureTimeChanged changeDepartureTime(LocalDateTime newTime){
        if (newTime != this.departureDateTime && newTime.isAfter(LocalDateTime.now())){
            this.departureDateTime = newTime;
            return new RSDemandDepartureTimeChanged(rsDemandId,newTime);
        }
        throw new IllegalArgumentException("Invalid date argument");
    }
    public RSDemandPickUpAddressChanged changePickUpAddress(Address newPickUpAddress, GeoPoint newGeoPoint){
        if (newPickUpAddress != this.departureAddress){
            this.departureAddress = newPickUpAddress;
            return new RSDemandPickUpAddressChanged(this.rsDemandId, newPickUpAddress, newGeoPoint);
        }
        throw new IllegalArgumentException("Invalid address argument");
    }
    public boolean expired(){
        return LocalDateTime.now().isAfter(departureDateTime);
    }
}

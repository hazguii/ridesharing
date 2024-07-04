package com.rs.domain.offer;

import java.time.LocalDateTime;

import com.ddd.AggregateRoot;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.rs.domain.offer.events.*;
import com.rs.exceptions.CommandRejectedException;
import com.rs.exceptions.EntityNotFoundException;

import io.quarkus.scheduler.Scheduled;
import lombok.Getter;

@Getter
public class RSOffer implements AggregateRoot<RSOfferId>{
    private RSOfferId rsOfferId;
    private UserId userId;
    private final Address departureAddress;
    private final Address destinationAddress;
    private NumberOfSeats numberOfAvailableSeats;
    private LocalDateTime departureDateTime;
    private boolean cancelled=false;
    private boolean published=false;
    private boolean closed = false;


    @JsonCreator
    public static RSOffer of(UserId userId, Address departureAddress, Address destinationAddress,
    NumberOfSeats numberOfAvailableSeats, LocalDateTime departureDateTime) throws EntityNotFoundException{
        if(userId== null){
            throw new EntityNotFoundException("cannot create an offer without owner");
        }
        return new RSOffer(new RSOfferId(), userId, departureAddress, destinationAddress, numberOfAvailableSeats, departureDateTime,false,false,false);
    }
    
    private RSOffer(RSOfferId rsOfferId, UserId userId, Address departureAddress, Address destinationAddress,
             NumberOfSeats numberOfAvailableSeats, LocalDateTime departureDateTime, boolean cancelled,
            boolean published, boolean closed) {
        this.rsOfferId = rsOfferId;
        this.userId = userId;
        this.departureAddress = departureAddress;
        this.destinationAddress = destinationAddress;
        this.numberOfAvailableSeats = numberOfAvailableSeats;
        this.departureDateTime = departureDateTime;
        this.cancelled = cancelled;
        this.published = published;
        this.closed = closed;
    }


    private RSOffer(UserId userId, Address departureAddress, Address destinationAddress,
            NumberOfSeats numberOfAvailableSeats, LocalDateTime departureDateTime) {
        this.userId = userId;
        this.departureAddress = departureAddress;
        this.destinationAddress = destinationAddress;
        this.numberOfAvailableSeats = numberOfAvailableSeats;
        this.departureDateTime = departureDateTime;
    }



    @Override
    public RSOfferId id() {
        return rsOfferId;
    }
    

    public RSOfferPublished publish() throws CommandRejectedException{
        if(!cancelled){
            published=true;
            return new RSOfferPublished(rsOfferId, userId, departureDateTime);
        }else throw new CommandRejectedException("Cannot publish a cancelled offer" );
    }
    public RSOfferCancelled cancel()throws CommandRejectedException{
        if(!cancelled){
            cancelled=true;
            return new RSOfferCancelled(rsOfferId);
        }
        throw new CommandRejectedException("already cancelled rsoffer");
    }
    @Scheduled(every = "500s")
    public RSOfferClosed close()throws CommandRejectedException{
        if (cancelled) throw new CommandRejectedException("offer with id " + rsOfferId + " is cancelled");
        if (!closed){
            closed = true;
            return new RSOfferClosed(rsOfferId);
        }
        throw new CommandRejectedException("already closed rsoffer with id: "+ rsOfferId);
    }
    public NumberOfAvailableSeatsChanged changeNumberOfAvailableSeats(NumberOfSeats numberOfSeats) throws CommandRejectedException{
        if (!cancelled || !closed){
        this.numberOfAvailableSeats=numberOfSeats;
        return new NumberOfAvailableSeatsChanged(rsOfferId,numberOfSeats);
        } else throw new CommandRejectedException("Cannot edit this offer, it might be cancelled or closed");
    }
    public DepartureTimeChanged changeDepartureTime(LocalDateTime departureTime)throws CommandRejectedException{
        if (!cancelled || !closed){
        this.departureDateTime = departureTime;
        return new DepartureTimeChanged(rsOfferId,departureTime);
    } else throw new CommandRejectedException("Cannot edit this offer, it might be cancelled or closed");
}
    public boolean expired(){
        return LocalDateTime.now().isAfter(departureDateTime);
    }
}

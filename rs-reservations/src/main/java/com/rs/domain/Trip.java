package com.rs.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ddd.AggregateRoot;
import com.ddd.Command;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.rs.domain.events.DriverNotifiedBeforePickupDateArrives;
import com.rs.domain.events.PassengerNotifiedBeforePickupDateArrives;
import com.rs.domain.events.RSApplicationAccepted;
import com.rs.domain.events.RSApplicationCancelled;
import com.rs.domain.events.RSApplicationRejected;
import com.rs.domain.events.RSApplicationSubmitted;
import com.rs.domain.events.TripCancelled;
import com.rs.exceptions.CommandRejectedException;
import com.rs.exceptions.EntityNotFoundException;

import lombok.Getter;

@Getter
public class Trip implements AggregateRoot<TripId>{
    private TripId tripId;
    private List<RSApplication> pendingApplications = new ArrayList<RSApplication>();
    private List<RSApplication> acceptedApplications = new ArrayList<RSApplication>();
    private List<RSApplication> rejectedApplications = new ArrayList<RSApplication>();
    private LocalDateTime tripDepartureTime;
    private boolean cancelled = false;
    private boolean passengerNotified = false;
    private boolean driverNotified = false;

    @JsonCreator
    public static Trip trip( TripId tripId, LocalDateTime tripDepartureTime){
        return new Trip(tripId, tripDepartureTime);
    }
    
    private Trip(TripId tripId, LocalDateTime tripDepartureTime) {
        this.tripId = tripId;
        this.tripDepartureTime = tripDepartureTime;
    }

    @Override
    public TripId id() {
        return this.tripId;
    }

    public RSApplicationAccepted acceptRSApplication(RSApplication application) throws EntityNotFoundException{
        if(pendingApplications.contains(application)){
            application.setAccepted(true);
            acceptedApplications.add(application);
            pendingApplications.remove(application);
            return new RSApplicationAccepted(tripId,application.getRsApplicationId());
        }

        throw new EntityNotFoundException(" This trip doesn't have an application with this id: " + application.rsApplicationId.stringValue());
    
    }

   public RSApplicationRejected rejectRSApplication(RSApplication application) throws EntityNotFoundException{
    if(pendingApplications.contains(application)){
        application.setAccepted(false);
        rejectedApplications.add(application);
        pendingApplications.remove(application);
        return new RSApplicationRejected(tripId, application.getRsApplicationId()); 
    }
        
    throw new EntityNotFoundException("This trip doesn't have an application with this id: " + application.rsApplicationId.stringValue());
    
    }

    public RSApplicationSubmitted submitApplication(RSApplication rsApplication) throws CommandRejectedException{
        if(!cancelled){
        pendingApplications.add(rsApplication);
        return new RSApplicationSubmitted (tripId, rsApplication.getRsApplicationId());
        }
        throw new CommandRejectedException("This trip has been closed");

    }
    public RSApplicationCancelled cancelApplication(RSApplicationId rsApplicationId) throws EntityNotFoundException{
        
        for (RSApplication application:pendingApplications){
            if (application.getRsApplicationId().id().compareTo(rsApplicationId.id())==0) {
                RSApplication app = application; 
                pendingApplications.remove(app);
                return new RSApplicationCancelled (tripId, rsApplicationId);
            }
            
        } 
        throw new EntityNotFoundException("The application with the id " + rsApplicationId.stringValue() + "does not exist.");
    }
    public TripCancelled cancel() throws CommandRejectedException{
        if(!cancelled){
            cancelled = true;
            return new TripCancelled(tripId);
        }else{
            throw new CommandRejectedException("Trip already cancelled");
        }
    }
    public DriverNotifiedBeforePickupDateArrives notifyDriver(){
        driverNotified = true;
        return new DriverNotifiedBeforePickupDateArrives(tripId);
    }
    public PassengerNotifiedBeforePickupDateArrives notifyPassenger(){
        passengerNotified = true;
        return new PassengerNotifiedBeforePickupDateArrives(tripId);
    }
    public RSApplication findRSapplicationById(RSApplicationId applicationId) throws EntityNotFoundException{
        for(RSApplication app : pendingApplications){
            if (app.getRsApplicationId().id().compareTo(applicationId.id())==0)
            return app;
        }
        throw new EntityNotFoundException("There is no application with this id: " + applicationId.stringValue());
    }

}
 

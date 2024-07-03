package com.rs.application;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import com.rs.domain.RSApplication;
import com.rs.domain.RSApplicationId;
import com.rs.domain.RSOfferPricingId;
import com.rs.domain.Trip;
import com.rs.domain.TripId;
import com.rs.domain.TripRepository;
import com.rs.domain.RSDemandId;
import com.rs.domain.RSOfferId;
import com.rs.domain.events.DriverNotifiedBeforePickupDateArrives;
import com.rs.domain.events.PassengerNotifiedBeforePickupDateArrives;
import com.rs.domain.events.RSApplicationAccepted;
import com.rs.domain.events.RSApplicationCancelled;
import com.rs.domain.events.RSApplicationRejected;
import com.rs.domain.events.RSApplicationSubmitted;
import com.rs.domain.events.TripCancelled;
import com.rs.domain.events.TripCreated;
import com.rs.exceptions.CommandRejectedException;
import com.rs.exceptions.EntityNotFoundException;

import io.quarkus.scheduler.Scheduled;
@ApplicationScoped
public class TripServiceImpl implements TripService{
    @Inject
    TripRepository tripRepository;

    @Inject
    @Channel("trip-created")
    Emitter<TripCreated> tripCreatedEmitter;

    @Inject
    @Channel("rsApplication-accepted")
    Emitter<RSApplicationAccepted> rsApplicationAcceptedEmitter;

    @Inject
    @Channel("rsApplication-rejected")
    Emitter<RSApplicationRejected> rsApplicationRejectedEmitter;

    @Inject
    @Channel("rsApplication-submitted")
    Emitter<RSApplicationSubmitted> rsApplicationSubmittedEmitter;

    @Inject
    @Channel("rsApplication-cancelled")
    Emitter<RSApplicationCancelled> rsApplicationCancelledEmitter;

    @Inject
    @Channel("trip-cancelled")
    Emitter<TripCancelled> tripCancelledEmitter;

    @Inject
    @Channel("driver-notified-before-pickup-date-arrived")
    Emitter<DriverNotifiedBeforePickupDateArrives> driverNotifiedBeforePickupDateArrivesEmitter;

    @Inject
    @Channel("passenger-notified-before-pickup-date-arrived")
    Emitter<PassengerNotifiedBeforePickupDateArrives> passengerNotifiedBeforePickupDateArrivesEmitter;

    @Override
    public void create(RSOfferId rsOfferId, LocalDateTime departureTime) {
        TripId tripId = new TripId(rsOfferId.value());
        Trip trip = Trip.trip(tripId, departureTime);
        tripRepository.save(trip);
        tripCreatedEmitter.send(new TripCreated(trip.id()));
    }

    
    @Override
    public void submitRSApplication(TripId tripId,RSDemandId rsDemandId) throws CommandRejectedException, EntityNotFoundException{
        RSApplicationId applicationId = new RSApplicationId(rsDemandId.value());
        RSApplication application = new RSApplication(applicationId, tripId);
        Optional<Trip> trip = tripRepository.lookup(tripId);
        if (trip.isPresent()){
            RSApplicationSubmitted e = trip.get().submitApplication(application);
            tripRepository.save(trip.get());
            rsApplicationSubmittedEmitter.send(e);

        }else{
            throw new EntityNotFoundException("There is no trip with the following id: "+tripId.stringValue());
        }
    }

    @Override
    public void cancelRSApplication(RSApplicationId rsApplicationId) throws CommandRejectedException,EntityNotFoundException{
        Optional<Trip> trip = tripRepository.findTripByApplicationId(rsApplicationId);
        if (trip.isPresent()){
            RSApplicationCancelled e = trip.get().cancelApplication(rsApplicationId);
            tripRepository.save(trip.get());
            rsApplicationCancelledEmitter.send(e);

        }else{
            throw new EntityNotFoundException("no trip with id "+rsApplicationId.stringValue());
        }
        
    }
    @Override
    public void cancelTrip(TripId tripId) throws CommandRejectedException,EntityNotFoundException{
        Optional<Trip> trip = tripRepository.lookup(tripId);
        if (trip.isPresent()){
            TripCancelled e = trip.get().cancel();
            tripRepository.save(trip.get());
            tripCancelledEmitter.send(e);

        }else{
            throw new EntityNotFoundException("There is no trip with the following id: "+tripId.stringValue());
        }
        
    }


    @Override
    public void acceptRSApplication(TripId tripId, RSApplicationId applicationId) throws EntityNotFoundException{
      Optional<Trip> trip = tripRepository.lookup(tripId);
        if(trip.isPresent()){ 
            RSApplication app= trip.get().findRSapplicationById(applicationId) ; //find application 
                RSApplicationAccepted acceptedApp = trip.get().acceptRSApplication(app);
                tripRepository.save(trip.get());
                rsApplicationAcceptedEmitter.send(acceptedApp);
            }
        throw new EntityNotFoundException("There is no trip with the following id: " + tripId.stringValue());
            
    }

    @Override
    public void rejectRSApplication(TripId tripId,RSApplicationId applicationId) throws EntityNotFoundException{
        Optional<Trip> trip = tripRepository.lookup(tripId);
        if(trip.isPresent()){ 
                RSApplication app= trip.get().findRSapplicationById(applicationId) ; //find application 
                RSApplicationRejected rejectedApp = trip.get().rejectRSApplication(app);
                tripRepository.save(trip.get());
                rsApplicationRejectedEmitter.send(rejectedApp);
            }
            throw new EntityNotFoundException("There is no trip with the following id: "+tripId.stringValue());

    }

    @Scheduled (every = "10s")
    @Override
    public void notifyPassengerBeforePickupDateArrives(){

        List<Trip> trips = tripRepository.findAllTripsWithNonNotifiedPassengers();
        
        LocalDateTime now = LocalDateTime.now();
        for (Trip trip:trips){
            System.out.println(ChronoUnit.MINUTES.between(now, trip.getTripDepartureTime()));
            if (ChronoUnit.MINUTES.between(now, trip.getTripDepartureTime()) < 15){
                PassengerNotifiedBeforePickupDateArrives event = trip.notifyPassenger();
                tripRepository.save(trip);
                passengerNotifiedBeforePickupDateArrivesEmitter.send(event);
            }
        }
    }

    @Scheduled (every = "10s")
    @Override
    public void notifyDriverBeforePickupDateArrives(){
        List<Trip> trips = tripRepository.findAllTripsWithNonNotifiedDrivers();
        LocalDateTime now = LocalDateTime.now();
        for (Trip trip:trips){
            System.out.println(ChronoUnit.MINUTES.between(now, trip.getTripDepartureTime()));
            if (ChronoUnit.MINUTES.between(now, trip.getTripDepartureTime()) < 30){
            DriverNotifiedBeforePickupDateArrives event = trip.notifyDriver();
            tripRepository.save(trip);
            driverNotifiedBeforePickupDateArrivesEmitter.send(event);
        }
    }
    }
   
    
}

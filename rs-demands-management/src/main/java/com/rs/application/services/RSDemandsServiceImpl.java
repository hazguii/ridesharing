package com.rs.application.services;

import com.rs.domain.demand.*;
import com.rs.domain.demand.events.*;
import io.quarkus.scheduler.Scheduled;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RSDemandsServiceImpl implements RSDemandsService {

    @Inject
    RSDemandsRepository demandRepository;

    @Inject
    @Channel("rsdemand-created")
    Emitter<RSDemandCreated> rsDemandCreatedEmitter;

    @Inject
    @Channel("rsdemand-cancelled")
    Emitter<RSDemandCancelled> rsDemandCancelledEmitter;

    @Inject
    @Channel("rsdemand-closed")
    Emitter<RSDemandClosed> rsDemandClosedEmitter;

    @Inject
    @Channel("rsdemand-published")
    Emitter<RSDemandPublished> rsDemandPublishedEmitter;

    @Inject
    @Channel("rsdemand-pickup-address-changed")
    Emitter<RSDemandPickUpAddressChanged> rsDemandPickUpAddressChangedEmitter;

    @Inject
    @Channel("rsdemand-departure-time-changed")
    Emitter<RSDemandDepartureTimeChanged> rsDemandDepartureTimeChangedEmitter;

    @Override
    public RSDemandId createRSDemand(UserId userId, Address departureAddress, GeoPoint departureGeoPoint, Address destinationAddress, GeoPoint destinationGeoPoint, LocalDateTime departureDateTime) {
        RSDemand newDemand = RSDemand.of(
                userId,
                departureAddress,
                departureGeoPoint,
                destinationAddress,
                destinationGeoPoint,
                departureDateTime
        );
        demandRepository.save(newDemand);
        rsDemandCreatedEmitter.send(new RSDemandCreated(userId, newDemand.id()));
        return newDemand.id();
    }

    @Override
    public void cancelRSDemand(RSDemandId rsDemandId) {
        Optional<RSDemand> o = demandRepository.lookup(rsDemandId);
        if(o.isPresent()){
            RSDemandCancelled e = o.get().cancel();
            demandRepository.save(o.get());
            rsDemandCancelledEmitter.send(e);
        }else{
            throw new IllegalArgumentException("no demand with id "+rsDemandId.stringValue());
        }
    }

    @Override
    public void closeRSDemand(RSDemandId rsDemandId) {
        Optional<RSDemand> o = demandRepository.lookup(rsDemandId);
        if(o.isPresent() ){
            RSDemandClosed e = o.get().close();
            demandRepository.save(o.get());
            rsDemandClosedEmitter.send(e);
        }else{
            throw new IllegalArgumentException("no demand with id "+rsDemandId.stringValue());
        }
    }

    @Override
    public void publishRSDemand(RSDemandId rsDemandId) {
        Optional<RSDemand> o = demandRepository.lookup(rsDemandId);
        if(o.isPresent()){
            RSDemandPublished e = o.get().publish();
            demandRepository.save(o.get());
            rsDemandPublishedEmitter.send(e);
        }else{
            throw new IllegalArgumentException("no demand with id " + rsDemandId.stringValue());
        }
    }

    @Override
    public void changeRSDemandPickUpAddress(RSDemandId rsDemandId, Address newAddress, GeoPoint newGeoPoint) {
        Optional<RSDemand> o = demandRepository.lookup(rsDemandId);
        if (o.isPresent()){
            RSDemandPickUpAddressChanged e = o.get().changePickUpAddress(newAddress, newGeoPoint);
            demandRepository.save(o.get());
            rsDemandPickUpAddressChangedEmitter.send(e);
        }else{
            throw new IllegalArgumentException("no demand with id "+rsDemandId.stringValue());
        }
    }

    @Override
    public void changeRSDemandDepartureTime(RSDemandId rsDemandId, LocalDateTime departureDateTime) {
        Optional<RSDemand> o = demandRepository.lookup(rsDemandId);
        if (o.isPresent()){
            RSDemandDepartureTimeChanged e = o.get().changeDepartureTime(departureDateTime);
            demandRepository.save(o.get());
            rsDemandDepartureTimeChangedEmitter.send(e);
        }else{
            throw new IllegalArgumentException("no demand with id "+rsDemandId.stringValue());
        }
    }

    @Override
    @Scheduled(every = "60s")
    public void closeExpiredRSDemands() {
        List<RSDemand> demands = new ArrayList<>();
        demands = demandRepository.findAllUnclosedDemands();
        for(RSDemand demand:demands){
            if (demand.expired()) closeRSDemand( demand.id());
        }
    }
}

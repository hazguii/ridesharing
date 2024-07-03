package com.rs.application.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.rs.api.dtos.RsOfferDto;
import com.rs.converter.RsOfferConverter;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import com.rs.domain.offer.*;
import com.rs.domain.offer.events.*;
import com.rs.exceptions.CommandRejectedException;
import com.rs.exceptions.EntityNotFoundException;

import io.quarkus.scheduler.Scheduled;


@ApplicationScoped
public class RSOfferServiceImpl implements RSOfferService {
    @Inject
    RSOfferRepository offersRepository;

    @Inject
    @Channel("rsoffer-created")
    Emitter<RSOfferCreated> rsOfferCreatedEmitter;
    @Inject
    @Channel("rsoffer-cancelled")
    Emitter<RSOfferCancelled> rsOfferCancelledEmitter;
    @Inject
    @Channel("rsoffer-published")
    Emitter<RSOfferPublished> rsOfferPublishedEmitter;
    @Inject
    @Channel("rsoffer-closed")
    Emitter<RSOfferClosed> rsOfferClosedEmitter;
    @Inject
    @Channel("numberofavailableseats-changed")
    Emitter<NumberOfAvailableSeatsChanged> numberOfAvailableSeatsChangedEmitter;
    @Inject
    @Channel("departuretime-changed")
    Emitter<DepartureTimeChanged> departureTimeChangedEmitter;

    @Override
    public List<RsOfferDto> getAllRsOffers(){
        return offersRepository.findAll().stream().map(rsOffer -> RsOfferConverter.newInstance().convert(rsOffer)).collect(Collectors.toList());
    }
    @Override
    public RSOfferId createRSOffer(UserId userId,Address departureAddress, Address destinationAddress, NumberOfSeats numberOfAvailableSeats, LocalDateTime departureDateTime) throws EntityNotFoundException{
        RSOffer newOffer = RSOffer.of(
            userId,
            departureAddress,
            destinationAddress,
            numberOfAvailableSeats,
            departureDateTime
            );
        offersRepository.save(newOffer);
        rsOfferCreatedEmitter.send(new RSOfferCreated(userId, newOffer.id()));
        return newOffer.id();
    }
    @Override
    public void publishRSOffer(UserId userId,RSOfferId rsOfferId) throws EntityNotFoundException, CommandRejectedException {
            Optional<RSOffer> o = offersRepository.lookup(rsOfferId);
        if(o.isPresent()){
            RSOfferPublished e = o.get().publish();
            offersRepository.save(o.get());
            rsOfferPublishedEmitter.send(e);
        }
        else{
            throw new EntityNotFoundException("offer not found");
        }
    }
    @Override
    public void cancelRSOffer(UserId userId,RSOfferId rsOfferId) throws EntityNotFoundException, CommandRejectedException{
        Optional<RSOffer> o = offersRepository.lookup(rsOfferId);
        if(o.isPresent()){
            RSOfferCancelled e = o.get().cancel();
            offersRepository.save(o.get());
            rsOfferCancelledEmitter.send(e);
        }else{
            throw new EntityNotFoundException("no offer with id "+rsOfferId.stringValue());
        }
    }
    @Override
    public void changeNumberOfAvailableSeats(UserId userId, RSOfferId rsOfferId, NumberOfSeats numberOfSeats) throws EntityNotFoundException, CommandRejectedException{
        Optional<RSOffer> offer = offersRepository.lookup(rsOfferId);
        if (offer.isPresent()){
            NumberOfAvailableSeatsChanged e = offer.get().changeNumberOfAvailableSeats(numberOfSeats);
            offersRepository.save(offer.get());
            numberOfAvailableSeatsChangedEmitter.send(e);
        }else{
            throw new EntityNotFoundException("no offer with id "+rsOfferId.stringValue());
        }
    }
    @Override
    public void changeDepartureTime(UserId userId, RSOfferId rsOfferId, LocalDateTime departureTime) throws EntityNotFoundException, CommandRejectedException{
        Optional<RSOffer> offer = offersRepository.lookup(rsOfferId);
        if (offer.isPresent()){
            DepartureTimeChanged e = offer.get().changeDepartureTime(departureTime);
            offersRepository.save(offer.get());
            departureTimeChangedEmitter.send(e);
        }else{
            throw new EntityNotFoundException("no offer with id "+rsOfferId.stringValue());
        }
    }
    @Override
    public void closeRSOffer(RSOfferId rsOfferId) throws EntityNotFoundException, CommandRejectedException{
        Optional<RSOffer> offer = offersRepository.lookup(rsOfferId);
        if(offer.isPresent() ){

            RSOfferClosed e = offer.get().close();
            offersRepository.save(offer.get());
            rsOfferClosedEmitter.send(e);
        }else{
            throw new EntityNotFoundException("no offer with id "+rsOfferId.stringValue());
        }
    }
    @Override
    @Scheduled(every = "500s")
    public void closeExpiredRSOffers() throws EntityNotFoundException, CommandRejectedException{
        List<RSOffer> offers = new ArrayList<>();
        offers = offersRepository.findAllUnclosedOffers();
        for(RSOffer offer:offers){
            if (offer.expired()) closeRSOffer( offer.getRsOfferId());
        }
    }

}

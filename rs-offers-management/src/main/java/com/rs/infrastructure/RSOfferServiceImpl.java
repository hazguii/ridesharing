package com.rs.infrastructure;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.rs.api.dtos.CreateRSOfferDTO;
import com.rs.api.dtos.RsOfferDto;
import com.rs.application.services.RSOfferService;
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
    public List<RsOfferDto> getAllRsOffers() {
        return offersRepository.findAll().stream().map(rsOffer -> RsOfferConverter.newInstance().convert(rsOffer)).collect(Collectors.toList());
    }

    @Override
    public RSOfferId createRSOffer(CreateRSOfferDTO createRSOfferDTO) throws EntityNotFoundException {
        UserId userId = new UserId(UUID.randomUUID());
        RSOffer savedRsOffer = offersRepository.save(RSOffer.of(
                userId,
                createRSOfferDTO.departureAddress(),
                createRSOfferDTO.destinationAddress(),
                createRSOfferDTO.availableSeatsNumber(),
                createRSOfferDTO.departureDateTime()
        ));
        rsOfferCreatedEmitter.send(new RSOfferCreated(userId, savedRsOffer.id()));
        return savedRsOffer.id();
    }

    @Override
    public void publishRSOffer(RSOfferId rsOfferId) throws EntityNotFoundException, CommandRejectedException {
        UserId userId = new UserId(UUID.randomUUID());
        Optional<RSOffer> o = offersRepository.lookup(rsOfferId);
        if (o.isPresent()) {
            RSOfferPublished e = o.get().publish();
            offersRepository.save(o.get());
            rsOfferPublishedEmitter.send(e);
        } else {
            throw new EntityNotFoundException("offer not found");
        }
    }

    @Override
    public RsOfferDto cancelRSOffer(RSOfferId rsOfferId) throws EntityNotFoundException, CommandRejectedException {
        Optional<RSOffer> optionalRSOffer = offersRepository.lookup(rsOfferId);
        if (optionalRSOffer.isPresent()) {
            RSOfferCancelled e = optionalRSOffer.get().cancel();
            RSOffer savedRsOffer = offersRepository.save(optionalRSOffer.get());
            rsOfferCancelledEmitter.send(e);
            return RsOfferConverter.newInstance().convert(savedRsOffer);
        } else {
            throw new EntityNotFoundException("There is no offer with this id: " + rsOfferId.stringValue());
        }
    }

    @Override
    public void changeNumberOfAvailableSeats(RSOfferId rsOfferId, NumberOfSeats numberOfSeats) throws EntityNotFoundException, CommandRejectedException {
        UserId userId = new UserId(UUID.randomUUID());
        Optional<RSOffer> offer = offersRepository.lookup(rsOfferId);
        if (offer.isPresent()) {
            NumberOfAvailableSeatsChanged e = offer.get().changeNumberOfAvailableSeats(numberOfSeats);
            offersRepository.save(offer.get());
            numberOfAvailableSeatsChangedEmitter.send(e);
        } else {
            throw new EntityNotFoundException("no offer with id " + rsOfferId.stringValue());
        }
    }

    @Override
    public void changeDepartureTime(RSOfferId rsOfferId, LocalDateTime departureTime) throws EntityNotFoundException, CommandRejectedException {
        UserId userId = new UserId(UUID.randomUUID());
        Optional<RSOffer> offer = offersRepository.lookup(rsOfferId);
        if (offer.isPresent()) {
            DepartureTimeChanged e = offer.get().changeDepartureTime(departureTime);
            offersRepository.save(offer.get());
            departureTimeChangedEmitter.send(e);
        } else {
            throw new EntityNotFoundException("no offer with id " + rsOfferId.stringValue());
        }
    }

    @Override
    public void closeRSOffer(RSOfferId rsOfferId) throws EntityNotFoundException, CommandRejectedException {
        Optional<RSOffer> offer = offersRepository.lookup(rsOfferId);
        if (offer.isPresent()) {

            RSOfferClosed e = offer.get().close();
            offersRepository.save(offer.get());
            rsOfferClosedEmitter.send(e);
        } else {
            throw new EntityNotFoundException("no offer with id " + rsOfferId.stringValue());
        }
    }

    @Override
    @Scheduled(every = "500s")
    public void closeExpiredRSOffers() throws EntityNotFoundException, CommandRejectedException {
        List<RSOffer> allUnclosedOffers = offersRepository.findAllUnclosedOffers();
        for (RSOffer offer : allUnclosedOffers) {
            if (offer.expired()) closeRSOffer(offer.getRsOfferId());
        }
    }

    @Override
    public RsOfferDto getOfferById(RSOfferId rsOfferId) throws EntityNotFoundException {
        Optional<RSOffer> rsOffer = offersRepository.findById(rsOfferId.value());
        if (rsOffer.isEmpty())
            throw new EntityNotFoundException("There is no offer with this id: " + rsOfferId.stringValue());
        return RsOfferConverter.newInstance().convert(rsOffer.get());
    }

}

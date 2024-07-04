package com.rs.application.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.rs.api.dtos.CreateRSOfferDTO;
import com.rs.api.dtos.RsOfferDto;
import com.rs.domain.offer.*;
import com.rs.exceptions.CommandRejectedException;
import com.rs.exceptions.EntityNotFoundException;

public interface RSOfferService {
    List<RsOfferDto> getAllRsOffers();
    RSOfferId createRSOffer(CreateRSOfferDTO createRSOfferDTO)throws EntityNotFoundException;
    void publishRSOffer(RSOfferId rsOfferId) throws EntityNotFoundException, CommandRejectedException;
    RsOfferDto cancelRSOffer(RSOfferId rsOfferId)throws EntityNotFoundException, CommandRejectedException;
    void closeRSOffer(RSOfferId rsOfferId)throws EntityNotFoundException, CommandRejectedException;
    void changeNumberOfAvailableSeats(RSOfferId rsOfferId, NumberOfSeats numberOfSeats)throws EntityNotFoundException, CommandRejectedException;
    void changeDepartureTime(RSOfferId rsOfferId, LocalDateTime departureTime)throws EntityNotFoundException, CommandRejectedException;
    void closeExpiredRSOffers() throws EntityNotFoundException, CommandRejectedException;
    RsOfferDto getOfferById(RSOfferId rsOfferId) throws EntityNotFoundException;
}

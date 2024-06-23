package com.rs.application.services;

import java.time.LocalDateTime;

import com.rs.domain.offer.*;
import com.rs.exceptions.CommandRejectedException;
import com.rs.exceptions.EntityNotFoundException;

public interface RSOfferService {
    RSOfferId createRSOffer(UserId userId, Address departureAddress, GeoPoint departureGeoPoint, Address destinationAddress, GeoPoint destinationGeoPoint, NumberOfSeats numberOfAvailableSeats, LocalDateTime departureDateTime)throws EntityNotFoundException;
    void publishRSOffer(UserId userId,RSOfferId rsOfferId) throws EntityNotFoundException, CommandRejectedException;
    void cancelRSOffer(UserId userId,RSOfferId rsOfferId)throws EntityNotFoundException, CommandRejectedException;
    void closeRSOffer(RSOfferId rsOfferId)throws EntityNotFoundException, CommandRejectedException;
    void changeNumberOfAvailableSeats(UserId userId, RSOfferId rsOfferId, NumberOfSeats numberOfSeats)throws EntityNotFoundException, CommandRejectedException;
    void changeDepartureTime(UserId userId, RSOfferId rsOfferId, LocalDateTime departureTime)throws EntityNotFoundException, CommandRejectedException;
    void closeExpiredRSOffers() throws EntityNotFoundException, CommandRejectedException;
}

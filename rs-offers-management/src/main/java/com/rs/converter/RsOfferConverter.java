package com.rs.converter;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import com.rs.api.dtos.RsOfferDto;
import com.rs.domain.offer.RSOffer;
import lombok.Data;

import java.util.Objects;

@Data(staticConstructor = "newInstance")
public class RsOfferConverter implements Converter<RSOffer, RsOfferDto> {
    @Override
    public RsOfferDto convert(RSOffer rsOffer) {
        if(Objects.isNull(rsOffer)) return null;
        return RsOfferDto.builder()
                .rsOfferId(rsOffer.getRsOfferId())
                .userId(rsOffer.getUserId())
                .departureAddress(rsOffer.getDepartureAddress())
                .destinationAddress(rsOffer.getDestinationAddress())
                .numberOfAvailableSeats(rsOffer.getNumberOfAvailableSeats())
                .departureDateTime(rsOffer.getDepartureDateTime())
                .cancelled(rsOffer.isCancelled())
                .published(rsOffer.isPublished())
                .closed(rsOffer.isClosed())
                .build();
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return null;
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return null;
    }
}

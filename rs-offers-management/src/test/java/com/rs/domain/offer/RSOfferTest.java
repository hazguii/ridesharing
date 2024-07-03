package com.rs.domain.offer;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.ddd.DomainEvent;
import com.rs.exceptions.EntityNotFoundException;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.common.constraint.Assert;

@QuarkusTest
public class RSOfferTest {
    
    @Test
    public void whenARSOfferIsCreatedThenItHasANonNullId()throws EntityNotFoundException{
        RSOffer o = RSOffer.of(new UserId(UUID.randomUUID()),
            new Address("city", "street", (short)0, "location"),
            new Address("city", "street", (short)0, "location"),
            new NumberOfSeats((short)4),
            LocalDateTime.now()
        );
        Assert.assertTrue(o.id()!=null && o.id().value()!=null);
    }

    @Test
    public void whenARSOfferIsCreatedThenItIsNotCancelledAndNotPublished()throws EntityNotFoundException{
        RSOffer o = RSOffer.of(new UserId(UUID.randomUUID()),
            new Address("city", "street", (short)0, "location"),
            new Address("city", "street", (short)0, "location"),
            new NumberOfSeats((short)4),
            LocalDateTime.now()
        );
        Assert.assertFalse(o.isCancelled());
        Assert.assertFalse(o.isPublished());
    }

    @Test
    public void whenANonCancelledRSOfferIsCancelledThenRaiseEvent()throws EntityNotFoundException{
        RSOffer o = RSOffer.of(new UserId(UUID.randomUUID()),
            new Address("city", "street", (short)0, "location"),
            new Address("city", "street", (short)0, "location"),
            new NumberOfSeats((short)4),
            LocalDateTime.now()
        );
        Assert.assertTrue(o.isCancelled());
       }

    @Test
    public void whenANonCancelledRSOfferIsPublishedThenRaiseEvent()throws EntityNotFoundException{
        RSOffer o = RSOffer.of(new UserId(UUID.randomUUID()),
            new Address("city", "street", (short)0, "location"),
            new Address("city", "street", (short)0, "location"),
            new NumberOfSeats((short)4),
            LocalDateTime.now()
        );
        
        Assert.assertTrue(o.isPublished());

    }


}

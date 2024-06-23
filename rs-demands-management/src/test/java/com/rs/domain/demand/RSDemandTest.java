package com.rs.domain.demand;

import com.ddd.DomainEvent;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import io.smallrye.common.constraint.Assert;

@QuarkusTest
public class RSDemandTest {

    @Test
    public void whenARSDemandIsCreatedThenItHasANonNullId(){
        RSDemand rsDemand = RSDemand.of(new UserId(UUID.randomUUID()),
                new Address("city", "street", (short)0, "location"),
                new GeoPoint(0,0),
                new Address("city", "street", (short)0, "location"),
                new GeoPoint(0,0),
                LocalDateTime.now()
        );
        Assert.assertTrue(rsDemand.id()!=null && rsDemand.id().value()!=null);
    }

    @Test
    public void whenARSDemandIsCreatedThenItIsNotCancelledAndNotPublishedAndNotClosed(){
        RSDemand rsDemand = RSDemand.of(new UserId(UUID.randomUUID()),
                new Address("city", "street", (short)0, "location"),
                new GeoPoint(0,0),
                new Address("city", "street", (short)0, "location"),
                new GeoPoint(0,0),
                LocalDateTime.now()
        );
        Assert.assertFalse(rsDemand.isCancelled());
        Assert.assertFalse(rsDemand.isPublished());
        Assert.assertFalse(rsDemand.isClosed());
    }

    @Test
    public void whenANonCancelledRSDemandIsCancelledThenRaiseEvent(){
        RSDemand rsDemand = RSDemand.of(new UserId(UUID.randomUUID()),
                new Address("city", "street", (short)0, "location"),
                new GeoPoint(0,0),
                new Address("city", "street", (short)0, "location"),
                new GeoPoint(0,0),
                LocalDateTime.now()
        );
        DomainEvent e = rsDemand.cancel();
        Assert.assertTrue(rsDemand.isCancelled());
        Assert.assertTrue(e.eventType().equals("RSDemandCancelled"));
        Assert.assertTrue(e.aggregateId().equals(rsDemand.id().stringValue()));
    }

    @Test
    public void whenANonCancelledRSDemandIsPublishedThenRaiseEvent(){
        RSDemand rsDemand = RSDemand.of(new UserId(UUID.randomUUID()),
                new Address("city", "street", (short)0, "location"),
                new GeoPoint(0,0),
                new Address("city", "street", (short)0, "location"),
                new GeoPoint(0,0),
                LocalDateTime.now()
        );
        DomainEvent e = rsDemand.publish();
        Assert.assertTrue(rsDemand.isPublished());
        Assert.assertTrue(e.eventType().equals("RSDemandPublished"));
        Assert.assertTrue(e.aggregateId().equals(rsDemand.id().stringValue()));
    }
}

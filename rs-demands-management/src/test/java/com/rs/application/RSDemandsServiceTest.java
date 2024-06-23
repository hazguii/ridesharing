package com.rs.application;

import static org.junit.jupiter.api.Assertions.*;

import com.rs.application.services.RSDemandsService;
import com.rs.domain.demand.Address;
import com.rs.domain.demand.GeoPoint;
import com.rs.domain.demand.RSDemandId;
import com.rs.domain.demand.UserId;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;

import io.quarkus.test.kafka.InjectKafkaCompanion;
import io.quarkus.test.kafka.KafkaCompanionResource;
import io.smallrye.reactive.messaging.kafka.companion.ConsumerTask;
import io.smallrye.reactive.messaging.kafka.companion.KafkaCompanion;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.UUID;

@QuarkusTest
@QuarkusTestResource(KafkaCompanionResource.class)
public class RSDemandsServiceTest {
    @InjectKafkaCompanion
    KafkaCompanion companion;
    @Inject
    RSDemandsService rsDemandService;

    @Test
    void WhenARSDemandIsCreatedThenRSDemandCreatedEventIsEmitted() {
        RSDemandId id = rsDemandService.createRSDemand(
                new UserId(UUID.randomUUID()),
                new Address("city", "street", (short)0, "location"),
                new GeoPoint((double)0, (double)0),
                new Address("city", "street", (short)0, "location"),
                new GeoPoint((double)0, (double)0),
                LocalDateTime.now()
        );

        ConsumerTask<String, String> events = companion.consumeStrings().fromTopics("rsdemand-created", 1);
        events.awaitCompletion();
        ConsumerRecord<String, String> r = events.getFirstRecord();
        assertTrue(r.value().contains(id.stringValue()));
        assertEquals(1, events.count());
    }

    @Test
    void WhenARSDemandIsCancelledThenRSDemandCancelledEventIsEmitted() {
        RSDemandId id = rsDemandService.createRSDemand(
                new UserId(UUID.randomUUID()),
                new Address("city", "street", (short)0, "location"),
                new GeoPoint((double)0, (double)0),
                new Address("city", "street", (short)0, "location"),
                new GeoPoint((double)0, (double)0),
                LocalDateTime.now()
        );
        rsDemandService.cancelRSDemand(id);

        ConsumerTask<String, String> events = companion.consumeStrings().fromTopics("rsdemand-cancelled",1);
        events.awaitCompletion();
        ConsumerRecord<String, String> r = events.getFirstRecord();
        assertEquals(1, events.count());
        assertTrue(r.value().contains(id.stringValue()));
    }

    @Test
    void WhenARSDemandIsPublishedThenRSDemandPublishedEventIsEmitted() {
        RSDemandId id = rsDemandService.createRSDemand(
                new UserId(UUID.randomUUID()),
                new Address("city", "street", (short)0, "location"),
                new GeoPoint((double)0, (double)0),
                new Address("city", "street", (short)0, "location"),
                new GeoPoint((double)0, (double)0),
                LocalDateTime.now()
        );
        rsDemandService.publishRSDemand(id);

        ConsumerTask<String, String> events = companion.consumeStrings().fromTopics("rsdemand-published", 1);
        events.awaitCompletion();
        ConsumerRecord<String, String> r = events.getFirstRecord();
        assertEquals(1, events.count());
        assertTrue(r.value().contains(id.stringValue()));
    }



}

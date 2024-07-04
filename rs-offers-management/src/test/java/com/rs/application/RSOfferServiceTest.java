package com.rs.application;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.inject.Inject;

import com.rs.application.services.RSOfferService;
import com.rs.domain.offer.Address;
import com.rs.domain.offer.NumberOfSeats;
import com.rs.domain.offer.RSOfferId;
import com.rs.domain.offer.UserId;
import com.rs.exceptions.EntityNotFoundException;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;

import io.quarkus.test.kafka.InjectKafkaCompanion;
import io.quarkus.test.kafka.KafkaCompanionResource;
import io.smallrye.reactive.messaging.kafka.companion.ConsumerTask;
import io.smallrye.reactive.messaging.kafka.companion.KafkaCompanion;


@QuarkusTest
@QuarkusTestResource(KafkaCompanionResource.class)
public class RSOfferServiceTest {

    @InjectKafkaCompanion 
    KafkaCompanion companion;
    @Inject
    RSOfferService rsOfferService;

    @Test
    void WhenARSOfferIsCreatedThenRSOfferCreatedEventIsEmitted() throws EntityNotFoundException{
//        RSOfferId id = rsOfferService.createRSOffer(
//            new UserId(UUID.randomUUID()),
//            new Address("city", "street", (short)0, "location"),
//            new Address("city", "street", (short)0, "location"),
//            new NumberOfSeats((short)4),
//            LocalDateTime.now()
//        );
//
//        ConsumerTask<String, String> events = companion.consumeStrings().fromTopics("rsoffer-created", 1);
//        events.awaitCompletion();
//        ConsumerRecord<String, String> r = events.getFirstRecord();
//        assertTrue(r.value().contains(id.stringValue()));
//        assertEquals(1, events.count());
//        //commentaire
    }


    @Test
    void WhenARSOfferIsCancelledThenRSOfferCancelledEventIsEmitted() throws EntityNotFoundException{
//        RSOfferId id = rsOfferService.createRSOffer(
//            new UserId(UUID.randomUUID()),
//            new Address("city", "street", (short)0, "location"),
//            new Address("city", "street", (short)0, "location"),
//            new NumberOfSeats((short)4),
//            LocalDateTime.now()
//        );
//
//        ConsumerTask<String, String> events = companion.consumeStrings().fromTopics("rsoffer-cancelled",1);
//        events.awaitCompletion();
//        ConsumerRecord<String, String> r = events.getFirstRecord();
//        assertEquals(1, events.count());
//        assertTrue(r.value().contains(id.stringValue()));
    }

    @Test
    void WhenARSOfferIsPublishedThenRSOfferPublishedEventIsEmitted() throws EntityNotFoundException{
//        RSOfferId id = rsOfferService.createRSOffer(
//            new UserId(UUID.randomUUID()),
//            new Address("city", "street", (short)0, "location"),
//            new Address("city", "street", (short)0, "location"),
//            new NumberOfSeats((short)4),
//            LocalDateTime.now()
//        );
//
//        ConsumerTask<String, String> events = companion.consumeStrings().fromTopics("rsoffer-published", 1);
//        events.awaitCompletion();
//        ConsumerRecord<String, String> r = events.getFirstRecord();
//        assertEquals(1, events.count());
//        assertTrue(r.value().contains(id.stringValue()));
    }

    
}
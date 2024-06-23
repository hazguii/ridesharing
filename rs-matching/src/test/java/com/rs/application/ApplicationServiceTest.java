package com.rs.application;

import com.rs.application.services.RSMatchingApplicationService;
import com.rs.domain.GeoPoint;
import com.rs.domain.RSProposalId;
import com.rs.domain.services.RSMatchingDomainService;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.kafka.InjectKafkaCompanion;
import io.quarkus.test.kafka.KafkaCompanionResource;
import io.smallrye.reactive.messaging.kafka.companion.ConsumerTask;
import io.smallrye.reactive.messaging.kafka.companion.KafkaCompanion;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@QuarkusTestResource(KafkaCompanionResource.class)
public class ApplicationServiceTest {
    @Inject
    RSMatchingApplicationService rsMatchingApplicationService;
    @Inject
    RSMatchingDomainService rsMatchingDomainService;
    @InjectKafkaCompanion
    KafkaCompanion companion;
    @Test
    public void whenRSDemandMatchRequestCreatedThenEmitEvent(){
        RSDemandId rsDemandId = rsMatchingApplicationService.createRSDemandMatchingRequest(
                new RSDemandId(UUID.randomUUID()),
                new GeoPoint(0,0),
                new GeoPoint(0,0),
                LocalDateTime.now()
        );
        ConsumerTask<String, String> events = companion.consumeStrings().fromTopics("rsdemandmatchrequest-created", 1);
        events.awaitCompletion();
        ConsumerRecord<String, String> r = events.getFirstRecord();
        assertTrue(r.value().contains(rsDemandId.stringValue()));
        assertEquals(1, events.count());
    }

    @Test
    public void whenRSOfferMatchRequestCreatedThenEmitEvent(){
        RSOfferId rsOfferId = rsMatchingApplicationService.createRSOfferMatchRequest(
                new RSOfferId(UUID.randomUUID()),
                new GeoPoint(0,0),
                new GeoPoint(0,0),
                LocalDateTime.now()
        );
        ConsumerTask<String, String> events = companion.consumeStrings().fromTopics("rsoffermatchrequest-created", 1);
        events.awaitCompletion();
        ConsumerRecord<String, String> r = events.getFirstRecord();
        assertTrue(r.value().contains(rsOfferId.stringValue()));
        assertEquals(1, events.count());
    }

    @Test
    public void whenRSDemandMatchRequestStoppedThenEmitEvent(){
        RSDemandId rsDemandId = rsMatchingApplicationService.createRSDemandMatchingRequest(
                new RSDemandId(UUID.randomUUID()),
                new GeoPoint(0,0),
                new GeoPoint(0,0),
                LocalDateTime.now()
        );
        rsMatchingApplicationService.stopRSDemandMatchRequest(rsDemandId);
        ConsumerTask<String, String> events = companion.consumeStrings().fromTopics("rsdemandmatchrequest-stopped", 1);
        events.awaitCompletion();
        ConsumerRecord<String, String> r = events.getFirstRecord();
        assertTrue(r.value().contains(rsDemandId.stringValue()));
        assertEquals(1, events.count());
    }

    @Test
    public void whenRSOfferMatchRequestStoppedThenEmitEvent(){
        RSOfferId rsOfferId = rsMatchingApplicationService.createRSOfferMatchRequest(
                new RSOfferId(UUID.randomUUID()),
                new GeoPoint(0,0),
                new GeoPoint(0,0),
                LocalDateTime.now()
        );
        rsMatchingApplicationService.stopRSOfferMatchRequest(rsOfferId);
        ConsumerTask<String, String> events = companion.consumeStrings().fromTopics("rsoffermatchrequest-stopped", 1);
        events.awaitCompletion();
        ConsumerRecord<String, String> r = events.getFirstRecord();
        assertTrue(r.value().contains(rsOfferId.stringValue()));
        assertEquals(1, events.count());
    }

    @Test
    public void whenRSDemandMatchRequestDepartureDateTimeChangedThenEmitEvent(){
        RSDemandId rsDemandId = rsMatchingApplicationService.createRSDemandMatchingRequest(
                new RSDemandId(UUID.randomUUID()),
                new GeoPoint(0,0),
                new GeoPoint(0,0),
                LocalDateTime.now()
        );
        rsMatchingApplicationService.changeRSDemandDepartureTime(rsDemandId,LocalDateTime.now().plusMinutes(10));
        ConsumerTask<String, String> events = companion.consumeStrings().fromTopics("rsdemandmatchrequest-departuredatetimechanged", 1);
        events.awaitCompletion();
        ConsumerRecord<String, String> r = events.getFirstRecord();
        assertTrue(r.value().contains(rsDemandId.stringValue()));
        assertEquals(1, events.count());
    }

    @Test
    public void whenRSOfferMatchRequestDepartureDateTimeChangedThenEmitEvent(){
        RSOfferId rsOfferId = rsMatchingApplicationService.createRSOfferMatchRequest(
                new RSOfferId(UUID.randomUUID()),
                new GeoPoint(0,0),
                new GeoPoint(0,0),
                LocalDateTime.now()
        );
        rsMatchingApplicationService.changeRSOfferDepartureTime(rsOfferId,LocalDateTime.now().plusMinutes(10));
        ConsumerTask<String, String> events = companion.consumeStrings().fromTopics("rsoffermatchrequest-departuredatetimechanged", 1);
        events.awaitCompletion();
        ConsumerRecord<String, String> r = events.getFirstRecord();
        assertTrue(r.value().contains(rsOfferId.stringValue()));
        assertEquals(1, events.count());
    }

    @Test
    public void whenRSDemandMatchRequestDepartureGeoPointChangedThenEmitEvent(){
        RSDemandId rsDemandId = rsMatchingApplicationService.createRSDemandMatchingRequest(
                new RSDemandId(UUID.randomUUID()),
                new GeoPoint(0,0),
                new GeoPoint(0,0),
                LocalDateTime.now()
        );
        rsMatchingApplicationService.changeRSDemandPickUpAddress(rsDemandId,new GeoPoint(0.1,0.1));
        ConsumerTask<String, String> events = companion.consumeStrings().fromTopics("rsdemandmatchrequest-departuregeopointchanged", 1);
        events.awaitCompletion();
        ConsumerRecord<String, String> r = events.getFirstRecord();
        assertTrue(r.value().contains(rsDemandId.stringValue()));
        assertEquals(1, events.count());
    }

    @Test
    public void whenRSProposalAcceptedThenEmitEvent(){
        RSProposalId rsProposalId = rsMatchingDomainService.createRSProposal(
                new RSDemandId(UUID.randomUUID()),
                new RSOfferId(UUID.randomUUID())
        );
        rsMatchingApplicationService.acceptRSProposal(rsProposalId);
        ConsumerTask<String, String> events = companion.consumeStrings().fromTopics("rsproposal-accepted", 1);
        events.awaitCompletion();
        ConsumerRecord<String, String> r = events.getFirstRecord();
        assertTrue(r.value().contains(rsProposalId.stringValue()));
        assertEquals(1, events.count());
    }

    @Test
    public void whenRSProposalRejectedThenEmitEvent(){
        RSProposalId rsProposalId = rsMatchingDomainService.createRSProposal(
                new RSDemandId(UUID.randomUUID()),
                new RSOfferId(UUID.randomUUID())
        );
        rsMatchingApplicationService.rejectRSProposal(rsProposalId);
        ConsumerTask<String, String> events = companion.consumeStrings().fromTopics("rsproposal-rejected", 1);
        events.awaitCompletion();
        ConsumerRecord<String, String> r = events.getFirstRecord();
        assertTrue(r.value().contains(rsProposalId.stringValue()));
        assertEquals(1, events.count());
    }
}

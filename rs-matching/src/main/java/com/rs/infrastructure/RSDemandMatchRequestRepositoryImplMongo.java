package com.rs.infrastructure;

import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import com.rs.application.RSDemandId;
import com.rs.domain.RSDemandMatchRequest;
import com.rs.domain.RSDemandMatchRequestRepository;
import com.rs.domain.RSOfferMatchRequest;
import org.bson.conversions.Bson;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.Document;


@ApplicationScoped
public class RSDemandMatchRequestRepositoryImplMongo implements RSDemandMatchRequestRepository {

    @Inject
    MongoClient mongoClient;
    @Inject
    private ObjectMapper jsonMapper;
    private static final double _MATCHINGRADIUS = 10;
    @Override
    public void save(RSDemandMatchRequest aggregate) {
        String json;
        try{
            Position rsDemandMatchRequestDeparturePosition = new Position(aggregate.getDepartureGeoPoint().longitude(), aggregate.getDepartureGeoPoint().latitude());
            Point rsDemandMatchRequestDeparturePoint = new Point(rsDemandMatchRequestDeparturePosition);
            Position rsDemandMatchRequestDestinationPosition = new Position(aggregate.getDestinationGeoPoint().longitude(), aggregate.getDestinationGeoPoint().latitude());
            Point rsDemandMatchRequestDestinationPoint = new Point(rsDemandMatchRequestDestinationPosition);
            json = jsonMapper.writeValueAsString(aggregate);
            Document document = Document.parse(json);
            document.append("rsDemandMatchRequestDeparturePoint", rsDemandMatchRequestDeparturePoint);
            document.append("rsDemandMatchRequestDestinationPoint", rsDemandMatchRequestDestinationPoint);
            Bson filter = Filters.eq("rsDemandtId.value",aggregate.id().value().toString());
            ReplaceOptions options = new ReplaceOptions().upsert(true);
            getCollection().replaceOne(filter,document,options);
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
    }

    @Override
    public Optional<RSDemandMatchRequest> lookUp(RSDemandId id) {
        Bson filter = Filters.eq("rsDemandId.value", id.value().toString());
        MongoCursor<Document> cursor = getCollection().find(filter).iterator();
        if(cursor.hasNext()){
            Document document = cursor.next();
            String json = document.toJson();
            try{
                RSDemandMatchRequest rsDemandMatchRequest = jsonMapper.readValue(json, RSDemandMatchRequest.class);
                return Optional.of(rsDemandMatchRequest);
            } catch (JsonMappingException e){
                e.printStackTrace();
            } catch (JsonProcessingException e){
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    @Override
    public List<RSDemandId> findMatchingDemands(RSOfferMatchRequest rsOfferMatchRequest) {

        List<RSDemandId> rsDemandIdList = new ArrayList<RSDemandId>();
        LocalDateTime offerTime = rsOfferMatchRequest.getDepartureDateTime(

        );
        Point rsOfferMatchRequestDeparturePoint = new Point(
                new Position(rsOfferMatchRequest.getDepartureGeoPoint().longitude(),
                        rsOfferMatchRequest.getDepartureGeoPoint().latitude()));

        Point rsOfferMatchRequestDestinationPoint = new Point(
                new Position(rsOfferMatchRequest.getDestinationGeoPoint().longitude(),
                        rsOfferMatchRequest.getDestinationGeoPoint().latitude()));

        getCollection().createIndex(Indexes.geo2dsphere("rsDemandMatchRequestDeparturePoint"));
        getCollection().createIndex(Indexes.geo2dsphere("rsDemandMatchRequestDestinationPoint"));

        MongoCursor<Document> cursorOnRSDemandMatchRequestNearBy = getCollection().find(
                Filters.near("rsDemandMatchRequestDeparturePoint",
                        rsOfferMatchRequestDeparturePoint,
                        _MATCHINGRADIUS,0.0)).filter(Filters.near("rsDemandMatchRequestDestinationPoint",
                rsOfferMatchRequestDestinationPoint,
                _MATCHINGRADIUS,0.0)).filter(Filters.eq("isMatching", true)).iterator();

        while (cursorOnRSDemandMatchRequestNearBy.hasNext()) {
            Document document = cursorOnRSDemandMatchRequestNearBy.next();
            String json = document.toJson();
            try {
                RSDemandMatchRequest rsDemandMatchRequest = jsonMapper.readValue(json, RSDemandMatchRequest.class);
                if (rsDemandMatchRequest.getDepartureDateTime().isBefore(offerTime.plusHours(1))
                        && rsDemandMatchRequest.getDepartureDateTime().isAfter(offerTime.minusHours(1))) {
                    rsDemandIdList.add(rsDemandMatchRequest.id());
                }
            } catch (JsonMappingException e){
                e.printStackTrace();
            } catch (JsonProcessingException e){
                e.printStackTrace();
            }
        }
        return rsDemandIdList;
    }

    @Override
    public List<RSDemandMatchRequest> findAll() {
        MongoCursor<Document> cursor = getCollection().find().iterator();
        List<RSDemandMatchRequest> rsDemandMatchRequestList = new ArrayList<RSDemandMatchRequest>();
        while (cursor.hasNext()) {
            Document document = cursor.next();
            String json = document.toJson();
            try {
                RSDemandMatchRequest rsDemandMatchRequest = jsonMapper.readValue(json, RSDemandMatchRequest.class);
                rsDemandMatchRequestList.add(rsDemandMatchRequest);
            } catch (JsonMappingException e){
                e.printStackTrace();
            } catch (JsonProcessingException e){
                e.printStackTrace();
            }
        }
        return rsDemandMatchRequestList;
    }

    private MongoCollection<Document> getCollection(){
        return mongoClient.getDatabase("rs").getCollection("rsDemandMatchingRequests");
    }
}

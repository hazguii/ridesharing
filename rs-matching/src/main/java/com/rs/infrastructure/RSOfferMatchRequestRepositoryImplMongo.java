package com.rs.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import com.rs.application.RSOfferId;
import com.rs.domain.RSDemandMatchRequest;
import com.rs.domain.RSOfferMatchRequest;
import com.rs.domain.RSOfferMatchRequestRepository;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RSOfferMatchRequestRepositoryImplMongo implements RSOfferMatchRequestRepository {

    @Inject
    MongoClient mongoClient;

    @Inject
    ObjectMapper jsonMapper;

    private static final double _MATCHINGRADIUS = 10;
    @Override
    public void save(RSOfferMatchRequest aggregate) {
        String json;
        try{
            Position rsOfferMatchRequestDeparturePosition=new Position(aggregate.getDepartureGeoPoint().longitude(),aggregate.getDepartureGeoPoint().latitude());
            Point rsOfferMatchRequestDeparturePoint=new Point(rsOfferMatchRequestDeparturePosition);
            Position rsOfferMatchRequestDestinationPosition=new Position(aggregate.getDestinationGeoPoint().longitude(),aggregate.getDestinationGeoPoint().latitude());
            Point rsOfferMatchRequestDestinationPoint=new Point(rsOfferMatchRequestDestinationPosition);
            json = jsonMapper.writeValueAsString(aggregate);
            Document document = Document.parse(json);
            document.append("rsOfferMatchRequestDeparturePoint",rsOfferMatchRequestDeparturePoint);
            document.append("rsOfferMatchRequestDestinationPoint",rsOfferMatchRequestDestinationPoint);
            Bson filter = Filters.eq("rsOfferId.value",aggregate.id().value().toString());
            ReplaceOptions options = new ReplaceOptions().upsert(true);
            getCollection().replaceOne(filter,document,options);
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
    }


    @Override
    public Optional<RSOfferMatchRequest> lookUp(RSOfferId id) {
        Bson filter = Filters.eq("rsOfferId.value",id.value().toString());
        MongoCursor<Document> cursor = getCollection().find(filter).iterator();
        if(cursor.hasNext()){
            Document document = cursor.next();
            String json = document.toJson();
            try{
                RSOfferMatchRequest rsOfferMatchRequest = jsonMapper.readValue(json,RSOfferMatchRequest.class);
                return Optional.of(rsOfferMatchRequest);
            } catch (JsonMappingException e){
                e.printStackTrace();
            } catch (JsonProcessingException e){
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    @Override
    public List<RSOfferId> findMatchingOffers(RSDemandMatchRequest rsDemandMatchRequest) {
        List<RSOfferId> rsOfferIdList = new ArrayList<RSOfferId>();
        LocalDateTime demandTime=rsDemandMatchRequest.getDepartureDateTime();

        Point rsDemandMatchRequestDeparturePoint = new Point(
                new Position(rsDemandMatchRequest.getDepartureGeoPoint().longitude(),
                        rsDemandMatchRequest.getDepartureGeoPoint().latitude()));

        Point rsDemandMatchRequestDestinationPoint = new Point(
                new Position(rsDemandMatchRequest.getDestinationGeoPoint().longitude(),
                        rsDemandMatchRequest.getDestinationGeoPoint().latitude()));

        getCollection().createIndex(Indexes.geo2dsphere("rsOfferMatchRequestDeparturePoint"));
        getCollection().createIndex(Indexes.geo2dsphere("rsOfferMatchRequestDestinationPoint"));

        MongoCursor<Document> cursorOnRSOfferMatchRequestNearBy = getCollection().find(
                Filters.near("rsOfferMatchRequestDeparturePoint",
                        rsDemandMatchRequestDeparturePoint,
                        _MATCHINGRADIUS,0.0)).filter(Filters.near("rsOfferMatchRequestDestinationPoint",
                rsDemandMatchRequestDestinationPoint,
                _MATCHINGRADIUS,0.0)).filter(Filters.eq("isMatching", true)).iterator();

        while (cursorOnRSOfferMatchRequestNearBy.hasNext()) {
            Document document = cursorOnRSOfferMatchRequestNearBy.next();
            String json = document.toJson();
            try {
                RSOfferMatchRequest rsOfferMatchRequest = jsonMapper.readValue(json, RSOfferMatchRequest.class);
                if(rsOfferMatchRequest.getDepartureDateTime().isBefore(demandTime.plusHours(1))
                            && rsOfferMatchRequest.getDepartureDateTime().isAfter(demandTime.minusHours(1))){
                        rsOfferIdList.add(rsOfferMatchRequest.id());
                }
            } catch (JsonMappingException e){
                e.printStackTrace();
            } catch (JsonProcessingException e){
                e.printStackTrace();
            }
        }
        return rsOfferIdList;
    }

    @Override
    public List<RSOfferMatchRequest> findAll() {
        MongoCursor<Document> cursor = getCollection().find().iterator();
        List<RSOfferMatchRequest> rsOfferMatchRequestList = new ArrayList<RSOfferMatchRequest>();
        while (cursor.hasNext()) {
            Document document = cursor.next();
            String json = document.toJson();
            try {
                RSOfferMatchRequest rsOfferMatchRequest = jsonMapper.readValue(json, RSOfferMatchRequest.class);
                rsOfferMatchRequestList.add(rsOfferMatchRequest);

            } catch (JsonMappingException e){
                e.printStackTrace();
            } catch (JsonProcessingException e){
                e.printStackTrace();
            }
        }
        return rsOfferMatchRequestList;
    }

    private MongoCollection<Document> getCollection(){
        return mongoClient.getDatabase("rs").getCollection("rsOfferMatchingRequests");
    }
}

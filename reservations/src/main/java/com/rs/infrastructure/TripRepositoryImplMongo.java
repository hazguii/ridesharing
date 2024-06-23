package com.rs.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.rs.domain.RSApplication;
import com.rs.domain.RSApplicationId;
import com.rs.domain.Trip;
import com.rs.domain.TripId;
import com.rs.domain.TripRepository;
@ApplicationScoped
public class TripRepositoryImplMongo implements TripRepository {
    @Inject
    MongoClient mongoClient;
    @Inject
    private ObjectMapper jsonMapper;
    
    @Override
    public List<Trip> findAll() {
        MongoCursor<Document> cursor = getCollection().find().iterator();
        List<Trip> offers = new ArrayList<>();
        while (cursor.hasNext()) {
            Document d = cursor.next();
            String json = d.toJson();
            try {
                Trip Trip = jsonMapper.readValue(json, Trip.class);
                offers.add(Trip);
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return offers;
    }

    private MongoCollection<Document> getCollection(){
        return mongoClient.getDatabase("rs").getCollection("trips");
    }

    @Override
    public void save(Trip trip) {
        String json;
        try {
            json = jsonMapper.writeValueAsString(trip);
            Document document = Document.parse(json);
            //document.put("id", trip.id().value().toString());
            Bson filter = Filters.eq("tripId.value", trip.getTripId().value().toString());
            ReplaceOptions options = new ReplaceOptions().upsert(true);
            getCollection().replaceOne(filter,document,options);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    

    @Override
    public Optional<Trip> lookup(TripId id) {
        Bson filter = Filters.eq("tripId.value", id.value().toString());
        MongoCursor<Document> cursor = getCollection().find(filter).iterator();
        if (cursor.hasNext()) {
            Document d = cursor.next();
            String json = d.toJson();
            try {
                Trip Trip = jsonMapper.readValue(json, Trip.class);
                return Optional.of(Trip);
            } catch (JsonMappingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Trip> findTripByApplicationId(RSApplicationId rsApplicationId) {
        
        Bson pendingApplicationsFilter = Filters.eq("rsApplicationId.id", rsApplicationId.stringValue());
        Bson filter = Filters.elemMatch("pendingApplications",pendingApplicationsFilter);
        MongoCursor<Document> cursor = getCollection().find(filter).iterator();
        if (cursor.hasNext()) {
            Document d = cursor.next();
            String json = d.toJson();
            try {
                Trip Trip = jsonMapper.readValue(json, Trip.class);
                return Optional.of(Trip);
            } catch (JsonMappingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return Optional.empty(); 
    }

   
    @Override
    public List<Trip> findAllTripsWithNonNotifiedPassengers(){
        Bson filter = Filters.eq("passengerNotified", false);
        MongoCursor<Document> cursor = getCollection().find(filter).iterator();
        List<Trip> trips = new ArrayList<>();
        while (cursor.hasNext()) {
            Document d = cursor.next();
            String json = d.toJson();
            try {
                Trip Trip = jsonMapper.readValue(json, Trip.class);
                trips.add(Trip);
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return trips;
    }
    
    @Override
    public List<Trip> findAllTripsWithNonNotifiedDrivers(){
        Bson filter = Filters.eq("driverNotified", false);
        MongoCursor<Document> cursor = getCollection().find(filter).iterator();
        List<Trip> trips = new ArrayList<>();
        while (cursor.hasNext()) {
            Document d = cursor.next();
            String json = d.toJson();
            try {
                Trip Trip = jsonMapper.readValue(json, Trip.class);
                trips.add(Trip);
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return trips;
    }
    

   
    
    
}

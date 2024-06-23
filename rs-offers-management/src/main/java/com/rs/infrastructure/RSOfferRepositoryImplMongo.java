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
import com.rs.domain.offer.RSOffer;
import com.rs.domain.offer.RSOfferId;

import com.rs.domain.offer.RSOfferRepository;

@ApplicationScoped
public class RSOfferRepositoryImplMongo implements RSOfferRepository{

    @Inject
    MongoClient mongoClient;
    @Inject
    private ObjectMapper jsonMapper;

    @Override
    public void save(RSOffer rsoffer) {       
        String json;
        try {
            json = jsonMapper.writeValueAsString(rsoffer);
            Document document = Document.parse(json);
            //document.put("id", rsoffer.id().value().toString());
            Bson filter = Filters.eq("rsOfferId.value", rsoffer.getRsOfferId().value().toString());
            ReplaceOptions options = new ReplaceOptions().upsert(true);
            getCollection().replaceOne(filter,document,options);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<RSOffer> lookup(RSOfferId offerId) {
        Bson filter = Filters.eq("rsOfferId.value", offerId.value().toString());
        MongoCursor<Document> cursor = getCollection().find(filter).iterator();
        if (cursor.hasNext()) {
            Document d = cursor.next();
            String json = d.toJson();
            try {
                RSOffer rsOffer = jsonMapper.readValue(json, RSOffer.class);
                return Optional.of(rsOffer);
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
    public List<RSOffer> findAll() {
        MongoCursor<Document> cursor = getCollection().find().iterator();
        List<RSOffer> offers = new ArrayList<>();
        while (cursor.hasNext()) {
            Document d = cursor.next();
            String json = d.toJson();
            try {
                RSOffer rsOffer = jsonMapper.readValue(json, RSOffer.class);
                offers.add(rsOffer);
            } catch (JsonMappingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return offers;
    }

    @Override
    public List<RSOffer> findAllUnclosedOffers() {
        MongoCursor<Document> cursor = getCollection().find().iterator();
        List<RSOffer> offers = new ArrayList<>();
        while (cursor.hasNext()) {
            Document d = cursor.next();
            String json = d.toJson();
            try {
                RSOffer rsOffer = jsonMapper.readValue(json, RSOffer.class);
                if(!rsOffer.isClosed()) offers.add(rsOffer);

            } catch (JsonMappingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return offers;
    }

    private MongoCollection<Document> getCollection(){
        return mongoClient.getDatabase("rs").getCollection("rsOffers");
    }
    
}

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
import com.rs.domain.pricing.RSOfferId;
import com.rs.domain.pricing.RSOfferPricing;
import com.rs.domain.pricing.RSOfferPricingRepository;

@ApplicationScoped
public class RSOfferPricingRepositoryImplMongo implements RSOfferPricingRepository{

    @Inject
    MongoClient mongoClient;
    @Inject
    private ObjectMapper jsonMapper;

    @Override
    public void save(RSOfferPricing rsoffer) {       
        String json;
        try {
            json = jsonMapper.writeValueAsString(rsoffer);
            Document document = Document.parse(json);
            //document.put("id", rsoffer.id().value().toString());
            Bson filter = Filters.eq("rsOfferId.value", rsoffer.id().value().toString());
            ReplaceOptions options = new ReplaceOptions().upsert(true);
            getCollection().replaceOne(filter,document,options);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

   
    @Override
    public List<RSOfferPricing> findAll() {
        MongoCursor<Document> cursor = getCollection().find().iterator();
        List<RSOfferPricing> offers = new ArrayList<>();
        while (cursor.hasNext()) {
            Document d = cursor.next();
            String json = d.toJson();
            try {
                RSOfferPricing rsOffer = jsonMapper.readValue(json, RSOfferPricing.class);
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

    

    private MongoCollection<Document> getCollection(){
        return mongoClient.getDatabase("rs").getCollection("rsOffersPricing");
    }


    @Override
    public Optional<RSOfferPricing> lookup(RSOfferId id) {
        Bson filter = Filters.eq("rsOfferId.value", id.value().toString());
        MongoCursor<Document> cursor = getCollection().find(filter).iterator();
        if (cursor.hasNext()) {
            Document d = cursor.next();
            String json = d.toJson();
            try {
                RSOfferPricing rsOffer = jsonMapper.readValue(json, RSOfferPricing.class);
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
    public List<RSOfferPricing> findAllUnclosedOffers() {
        // TODO Auto-generated method stub
        return null;
    }
    
}

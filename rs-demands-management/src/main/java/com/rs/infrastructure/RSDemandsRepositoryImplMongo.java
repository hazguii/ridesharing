package com.rs.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.rs.domain.demand.RSDemand;
import com.rs.domain.demand.RSDemandId;
import com.rs.domain.demand.RSDemandsRepository;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@ApplicationScoped
public class RSDemandsRepositoryImplMongo implements RSDemandsRepository {
    @Inject
    MongoClient mongoClient;

    @Inject
    private ObjectMapper jsonMapper;

    @Override
    public void save(RSDemand rsDemand) {
        String json;
        try{
            json = jsonMapper.writeValueAsString(rsDemand);
            Document document = Document.parse(json);
            Bson filter = Filters.eq("rsDemandId.value",rsDemand.getRsDemandId().value().toString());
            ReplaceOptions options = new ReplaceOptions().upsert(true);
            getCollection().replaceOne(filter,document,options);

        } catch (JsonProcessingException e){
            e.printStackTrace();
        }

    }

    @Override
    public Optional<RSDemand> lookup(RSDemandId rsDemandId) {
        Bson filter = Filters.eq("rsDemandId.value",rsDemandId.value().toString());
        MongoCursor<Document> cursor = getCollection().find(filter).iterator();
        if(cursor.hasNext()){
            Document document = cursor.next();
            String json = document.toJson();
            try{
                RSDemand rsDemand = jsonMapper.readValue(json,RSDemand.class);
                return Optional.of(rsDemand);
            } catch (JsonMappingException e){
                e.printStackTrace();
            } catch (JsonProcessingException e){
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    @Override
    public List<RSDemand> findAll() {
        MongoCursor<Document> cursor = getCollection().find().iterator();
        List<RSDemand> rsDemandList = new ArrayList<RSDemand>();
        while (cursor.hasNext()) {
            Document document = cursor.next();
            String json = document.toJson();
            try {
                RSDemand rsDemand = jsonMapper.readValue(json, RSDemand.class);
                rsDemandList.add(rsDemand);

            } catch (JsonMappingException e){
                e.printStackTrace();
            } catch (JsonProcessingException e){
                e.printStackTrace();
            }
        }
        return rsDemandList;
    }

    @Override
    public List<RSDemand> findAllUnclosedDemands() {
        Bson filter = Filters.eq("closed.value","false");
        MongoCursor<Document> cursor = getCollection().find(filter).iterator();
        List<RSDemand> demands = new ArrayList<>();
        while (cursor.hasNext()) {
            Document d = cursor.next();
            String json = d.toJson();
            try {
                RSDemand rsDemand = jsonMapper.readValue(json, RSDemand.class);
                demands.add(rsDemand);
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return demands;
    }

    private MongoCollection<Document> getCollection(){
        return mongoClient.getDatabase("rs").getCollection("rsDemands");
    }
}

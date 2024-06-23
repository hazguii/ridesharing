package com.rs.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.rs.application.RSDemandId;
import com.rs.application.RSOfferId;
import com.rs.domain.RSProposal;
import com.rs.domain.RSProposalId;
import com.rs.domain.RSProposalRepository;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RSProposalRepositoryImplMongo implements RSProposalRepository {
    @Inject
    MongoClient mongoClient;

    @Inject
    private ObjectMapper jsonMapper;

    @Override
    public void save(RSProposal aggregate) {
        String json;
        try{
            json = jsonMapper.writeValueAsString(aggregate);
            Document document = Document.parse(json);
            Bson filter = Filters.eq("rsProposalId.value",aggregate.id().value().toString());
            ReplaceOptions options = new ReplaceOptions().upsert(true);
            getCollection().replaceOne(filter,document,options);

        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
    }

    @Override
    public Optional<RSProposal> lookUp(RSProposalId rsProposalId) {

        Bson filter = Filters.eq("rsProposalId.value",rsProposalId.value().toString());
        MongoCursor<Document> cursor = getCollection().find(filter).iterator();
        if(cursor.hasNext()){
            Document document = cursor.next();
            String json = document.toJson();
            try{
                RSProposal rsProposal = jsonMapper.readValue(json,RSProposal.class);
                return Optional.of(rsProposal);
            } catch (JsonMappingException e){
                e.printStackTrace();
            } catch (JsonProcessingException e){
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    @Override
    public List<RSProposal> findAll() {
        MongoCursor<Document> cursor = getCollection().find().iterator();
        List<RSProposal> rsProposalList = new ArrayList<RSProposal>();
        while (cursor.hasNext()) {
            Document document = cursor.next();
            String json = document.toJson();
            try {
                RSProposal rsProposal = jsonMapper.readValue(json, RSProposal.class);
                rsProposalList.add(rsProposal);

            } catch (JsonMappingException e){
                e.printStackTrace();
            } catch (JsonProcessingException e){
                e.printStackTrace();
            }
        }
        return rsProposalList;
    }

    @Override
    public List<RSProposal> findByDemandId(RSDemandId rsDemandId) {
        Bson filter = Filters.eq("rsDemandId.value",rsDemandId.value().toString());
        MongoCursor<Document> cursor = getCollection().find(filter).iterator();
        List<RSProposal> rsProposalList = new ArrayList<RSProposal>();
        while (cursor.hasNext()) {
            Document document = cursor.next();
            String json = document.toJson();
            try {
                RSProposal rsProposal = jsonMapper.readValue(json, RSProposal.class);
                    rsProposalList.add(rsProposal);
            } catch (JsonMappingException e){
                e.printStackTrace();
            } catch (JsonProcessingException e){
                e.printStackTrace();
            }
        }
        return rsProposalList;
    }

    @Override
    public List<RSProposal> findByOfferId(RSOfferId rsOfferId) {
        Bson filter = Filters.eq("rsOfferId.value",rsOfferId.value().toString());
        MongoCursor<Document> cursor = getCollection().find(filter).iterator();
        List<RSProposal> rsProposalList = new ArrayList<RSProposal>();
        while (cursor.hasNext()) {
            Document document = cursor.next();
            String json = document.toJson();
            try {
                RSProposal rsProposal = jsonMapper.readValue(json, RSProposal.class);
                rsProposalList.add(rsProposal);
            } catch (JsonMappingException e){
                e.printStackTrace();
            } catch (JsonProcessingException e){
                e.printStackTrace();
            }
        }
        return rsProposalList;
    }

    @Override
    public List<RSProposal> findAcceptedProposalsByDemandId(RSDemandId rsDemandId) {
        return null;
    }


    private MongoCollection<Document> getCollection(){
        return mongoClient.getDatabase("rs").getCollection("rsProposals");
    }
}

package com.rs.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.rs.domain.Counter;
import com.rs.domain.CounterRepository;


@ApplicationScoped
public class RSCountersRepositoryMongodb implements CounterRepository{
    @Inject
    MongoClient mongoClient;

    private MongoCollection<Document> getCollection(){
        return mongoClient.getDatabase("rs").getCollection("counters");
    }

    @Override
    public Optional<Counter> findCounterByName(String counterName) {
        Bson filter = Filters.eq("counterName", counterName);
        MongoCursor<Document> cursor = getCollection().find(filter).iterator();
        if (cursor.hasNext()) {
            Document d = cursor.next();
            Counter c = new Counter(counterName, d.getLong("value"));
            return Optional.of(c);
        }
        return Optional.empty();
    }

    @Override
    public void incrementCounterValue(String counterName) {
        Bson filter = Filters.eq("name", counterName);
        Bson update = Updates.inc("value", 1l);
        UpdateOptions options = new UpdateOptions().upsert(true);
        getCollection().updateOne(filter,update,options);
    }

    @Override
    public void decrementCounterValue(String counterName) {
        Bson filter = Filters.eq("name", counterName);
        Bson update = Updates.inc("value", 1l);
        UpdateOptions options = new UpdateOptions().upsert(true);
        getCollection().updateOne(filter,update,options);
    }

    @Override
    public List<Counter> findAll() {
        MongoCursor<Document> cursor = getCollection().find().iterator();
        List<Counter> counters = new ArrayList<>();
        while (cursor.hasNext()) {
            Document d = cursor.next();
            counters.add(new Counter(d.getString("name"), d.getLong("value")));
        }
        return counters;
    }
    
}

package com.rs.infrastructure;

import java.util.ArrayList;
import java.util.List;

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
import com.rs.domain.Email;
import com.rs.domain.EmailRepository;
import com.rs.domain.RSApplicationId;
import com.rs.domain.TripId;
import com.rs.domain.User;

import io.quarkus.mailer.MailTemplate;
import io.quarkus.qute.Location;


@ApplicationScoped
public class EmailRepositoryImpl implements EmailRepository{
    
    @Inject
    MongoClient mongoClient;
    @Inject
    private ObjectMapper jsonMapper;
                                              
    @Inject
    @Location("templateNotifyDriverAboutCancelledApplication.html")
    MailTemplate templateNotifyDriverAboutCancelledApplication;

    @Inject
    @Location("templateNotifyPassengerAboutCancelledTrip.html")
    MailTemplate templateNotifyPassengerAboutCancelledTrip;

    @Inject
    @Location("templateRemindDriver.html")
    MailTemplate templateRemindDriver;

    @Inject
    @Location("templateRemindPassenger.html")
    MailTemplate templateRemindPassenger;

    @Override
    public List<Email> findAll() {
        MongoCursor<Document> cursor = getCollection().find().iterator();
        List<Email> Emails = new ArrayList<>();
        while (cursor.hasNext()) {
            Document d = cursor.next();
            String json = d.toJson();
            try {
                Email Email = jsonMapper.readValue(json, Email.class);
                Emails.add(Email);
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return Emails;
    }

    

   
    @Override
    public void save(Email email){
        String json;
        try {
            json = jsonMapper.writeValueAsString(email);
            Document document = Document.parse(json);
            //document.put("id", email.id().value().toString());
            Bson filter = Filters.eq("emailId.id", email.getEmailId().stringValue());
            ReplaceOptions options = new ReplaceOptions().upsert(true);
            getCollection().replaceOne(filter,document,options);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    
    private MongoCollection<Document> getCollection(){
        return mongoClient.getDatabase("rs").getCollection("Emails");
    }


    @Override
    public void notifyDriverAboutCancelledApplication(User driver, RSApplicationId rsApplicationId) {
        String htmlBody = templateNotifyDriverAboutCancelledApplication.to(driver.emailAddress()).subject("Application cancelled").data("user",driver).data("applicationid", rsApplicationId).templateInstance().render();
        Email email = Email.of(driver,"Application Cancelled", htmlBody);
        save(email);        
    }

    @Override
    public void notifyPassengerAboutCancelledTrip(User passenger, TripId tripId) {
        String htmlBody = templateNotifyPassengerAboutCancelledTrip.to(passenger.emailAddress()).subject("Trip cancelled").data("user",passenger).data("tripId", tripId).templateInstance().render();
        Email email = Email.of(passenger,"Trip Cancelled", htmlBody);
        save(email); 
    }


    @Override
    public List<Email> findAllUnsentEmails() {
        Bson filter = Filters.eq("sent",false);
        MongoCursor<Document> cursor = getCollection().find(filter).iterator();
        List<Email> Emails = new ArrayList<>();
        while (cursor.hasNext()) {
            Document d = cursor.next();
            String json = d.toJson();
            try {
                Email Email = jsonMapper.readValue(json, Email.class);
                Emails.add(Email);
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return Emails; 
    }




    @Override
    public void remindDriver(User driver, TripId tripId) {
        String htmlBody = templateRemindDriver.to(driver.emailAddress()).subject("Reminder").data("driver",driver).data("tripId", tripId).templateInstance().render();
        Email email = Email.of(driver,"Reminder", htmlBody);
        save(email); 
    }




    @Override
    public void remindPassenger(User passenger, TripId tripId) {
        String htmlBody = templateRemindPassenger.to(passenger.emailAddress()).subject("Reminder").data("passenger",passenger).data("tripId", tripId).templateInstance().render();
        Email email = Email.of(passenger,"Reminder", htmlBody);
        save(email); 
    }




    




     
}

package com.rs.domain;


import com.ddd.AggregateRoot;
import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;
@Getter
public class Email implements AggregateRoot<EmailId>{

    private EmailId emailId;
    private User user;
    private String object;
    private String body;
    private boolean sent = false;
    

    @JsonCreator
    public static Email of(User user, String object, String body){
        if(user== null){
            throw new IllegalArgumentException("cannot create an offer without owner");
        }
        return new Email(new EmailId(), user, object, body);
    }
    
    private Email(EmailId emailId, User user, String object, String body) {
        this.emailId = emailId;
        this.user = user;
        this.object = object;
        this.body = body;
    }


    @Override
    public EmailId id() {
        return emailId;
    }

    public void sent(){
        if (!sent){
            this.sent = true;
        }else{
        throw new IllegalArgumentException("email already sent");
    }}
    
    
}

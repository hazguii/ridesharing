package com.rs.api;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.rs.application.services.CounterService;
import com.rs.domain.Counter;
import com.rs.domain.CounterRepository;



@Path("/Counters")
public class CounterResource {

    @Inject
    CounterService CounterService;
    

    @Inject
    CounterRepository counterRepository;

    @GET
    public List<Counter> findAll() {
        return counterRepository.findAll();
    }

    
}
package com.rs.domain.demand;

import com.ddd.Repository;

import java.util.List;

public interface RSDemandsRepository extends Repository<RSDemand, RSDemandId > {
    List<RSDemand> findAll();
    List<RSDemand> findAllUnclosedDemands();
}

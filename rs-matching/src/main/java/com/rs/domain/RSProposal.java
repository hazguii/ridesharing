package com.rs.domain;

import com.ddd.AggregateRoot;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.rs.application.RSDemandId;
import com.rs.application.RSOfferId;
import com.rs.application.events.RSProposalAccepted;
import com.rs.application.events.RSProposalRejected;
import com.rs.domain.events.RSProposalCancelled;
import lombok.Getter;

import java.util.UUID;
@Getter
public class RSProposal implements AggregateRoot<RSProposalId> {
    private RSProposalId rsProposalId;
    private RSDemandId rsDemandId;
    private RSOfferId rsOfferId;
    private RSProposalStatus rsProposalStatus;
    @Override
    public RSProposalId id() {
        return rsProposalId;
    }
    private RSProposal(RSDemandId rsDemandId, RSOfferId rsOfferId){
        this.rsProposalId = new RSProposalId(UUID.randomUUID());
        this.rsDemandId = rsDemandId;
        this.rsOfferId = rsOfferId;
        this.rsProposalStatus = RSProposalStatus.ACTIVE;
    }

    private RSProposal(RSProposalId rsProposalId, RSDemandId rsDemandId, RSOfferId rsOfferId,RSProposalStatus rsProposalStatus) {
        this.rsProposalId = rsProposalId;
        this.rsDemandId = rsDemandId;
        this.rsOfferId = rsOfferId;
        this.rsProposalStatus = rsProposalStatus;
    }

    @JsonCreator
    public static RSProposal of(RSDemandId rsDemandId, RSOfferId rsOfferId){
        if(rsDemandId == null) throw new IllegalArgumentException("Cannot create proposal with null rsDemandId");
        if(rsOfferId == null) throw new IllegalArgumentException("Cannot create proposal with null rsOfferId");
        return new RSProposal(new RSProposalId(), rsDemandId, rsOfferId, RSProposalStatus.ACTIVE);
    }

    public RSProposalAccepted accept(){
        if (this.rsProposalStatus == RSProposalStatus.ACTIVE){
            this.rsProposalStatus = RSProposalStatus.ACCEPTED;
            return new RSProposalAccepted(this.id());
        }else {
            throw new IllegalStateException("Cannot accept inactive proposal with id" + this.rsProposalId);
        }
    }

    public RSProposalRejected reject(){
        if (this.rsProposalStatus == RSProposalStatus.ACTIVE){
            this.rsProposalStatus = RSProposalStatus.REJECTED;
            return new RSProposalRejected(this.id());
        }else {
            throw new IllegalStateException("Cannot reject inactive proposal with id " + this.rsProposalId);
        }
    }

    public RSProposalCancelled cancel(){
        if (this.rsProposalStatus != RSProposalStatus.CANCELLED){
            this.rsProposalStatus = RSProposalStatus.CANCELLED;
            return new RSProposalCancelled(this.id());

        }else{
            throw new IllegalStateException("Already cancelled proposal with id " + this.rsProposalId);
        }
    }
}

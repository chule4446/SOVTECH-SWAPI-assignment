package com.sovtech.microservices.graphqlserver.service;

import com.sovtech.microservices.graphqlserver.component.ServiceUtility;
import com.sovtech.microservices.graphqlserver.model.payloads.ResponsePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PeopleService {
    private ServiceUtility serviceUtility;

    @Autowired
    public void setServiceUtility(ServiceUtility serviceUtility) {

        this.serviceUtility = serviceUtility;
    }

    public Mono<ResponseEntity<ResponsePayload>> processRequest(HttpHeaders httpHeaders,String query) {
        return this.serviceUtility.processRequest(httpHeaders, query);
    }

}

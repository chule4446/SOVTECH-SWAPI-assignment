package com.sovtech.microservices.graphqlserver.controller;

import com.sovtech.microservices.graphqlserver.component.GraphQLProvider;
import com.sovtech.microservices.graphqlserver.model.payloads.ResponsePayload;
import com.sovtech.microservices.graphqlserver.service.PeopleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/swapi")
public class PeopleController {
    private final GraphQLProvider provider;
    @Autowired
    private final RestTemplate restTemplate;
    @Autowired
    PeopleService peopleService;

    @PostMapping
    public Mono<ResponseEntity<ResponsePayload>> getRequest(@RequestHeader HttpHeaders httpHeaders,
                                                            @RequestBody String query)  {
        return peopleService.processRequest(httpHeaders,query);

    }

}
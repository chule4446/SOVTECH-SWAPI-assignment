package com.sovtech.microservices.graphqlserver.model;

import lombok.Data;

import java.util.List;
@Data
public class Swapi {
    private String next;
    private List<People> results;
}
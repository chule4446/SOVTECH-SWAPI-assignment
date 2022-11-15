package com.sovtech.microservices.graphqlserver.model;
import lombok.Data;

@Data
public class People {
    private String name;
    private String height;
    private String mass;
    private String gender;
    private String homeworld;
}

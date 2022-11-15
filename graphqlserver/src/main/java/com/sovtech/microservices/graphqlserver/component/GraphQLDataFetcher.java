package com.sovtech.microservices.graphqlserver.component;

import com.sovtech.microservices.graphqlserver.model.Swapi;
import graphql.schema.DataFetcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
@RequiredArgsConstructor
public class GraphQLDataFetcher {

    @Value("${swapi.dev.url}")
    private String SWAPI_URL;
    @Autowired
   private final RestTemplate restTemplate;
    public DataFetcher<Swapi> getPeopleList(){
        log.info("Inside getPeopleList");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        return dataFetchingEnvironment -> restTemplate
                .exchange(SWAPI_URL + "/api/people", HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<Swapi>() {
                        }).getBody();
    }

    public DataFetcher<Swapi> getPersonByName(){
        log.info("Inside getPersonByName");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(SWAPI_URL + "/api/people")
                .queryParam("search", "Luke Skywalker");

            return dataFetchingEnvironment -> {
                String name = dataFetchingEnvironment.getArgument("name");
                return restTemplate.getForObject(UriComponentsBuilder.fromUriString(SWAPI_URL + "/api/people").
                        queryParam("search",name).build().toString(), Swapi.class);
            };
    }


    public  DataFetcher<Swapi> getPeopleListPerPage(){

        return dataFetchingEnvironment -> {
            String page = dataFetchingEnvironment.getArgument("page");
            return restTemplate.getForObject(UriComponentsBuilder.fromUriString("https://swapi.dev/api/people/").
                    queryParam("page",page).build().toString(), Swapi.class);
        };

    }
}

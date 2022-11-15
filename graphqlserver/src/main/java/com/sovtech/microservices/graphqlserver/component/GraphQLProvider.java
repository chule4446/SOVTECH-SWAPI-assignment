package com.sovtech.microservices.graphqlserver.component;


import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;

@Component
@Slf4j
@RequiredArgsConstructor
public class GraphQLProvider {
    private GraphQL graphQL;
    private final GraphQLDataFetcher dataFetcher;
    @Value("${graphql.path}")
    private String GRAPHQL_FILE_PATH;

    @PostConstruct
    public void init() {
        final Resource resource = new
                ClassPathResource(GRAPHQL_FILE_PATH);
        String sdl = null;
        try {
            sdl = Files.readString(resource.getFile().
                    toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).
                build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(TypeRuntimeWiring.newTypeWiring("Query")
                        .dataFetcher("people",dataFetcher.getPeopleList()))
                .type(TypeRuntimeWiring.newTypeWiring("Query")
                        .dataFetcher("person", dataFetcher.getPersonByName()))
                .type(TypeRuntimeWiring.newTypeWiring("Query")
                        .dataFetcher("page",dataFetcher.getPeopleListPerPage() ))

                .build();
    }

    @Bean
    public GraphQL initiateGraphQL() {
        return graphQL;
    }
}

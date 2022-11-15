package com.sovtech.microservices.graphqlserver.model.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sovtech.microservices.graphqlserver.model.payloads.header.Header;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Model api response.
 *
 * @author francisnginya Defines the schema of how all responses will be shown
 */
@Getter
@Setter
public class ModelApiResponse {
    @JsonProperty("header")
    private final Header apiHeaderResponse;
    @JsonProperty("body")
    private Object responseBodyObject;

    /**
     * Instantiates a new Model api response.
     */
    public ModelApiResponse() {
        this.apiHeaderResponse = new Header();
    }
}

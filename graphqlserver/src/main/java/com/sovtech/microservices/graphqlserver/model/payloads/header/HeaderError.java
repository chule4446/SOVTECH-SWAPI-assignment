package com.sovtech.microservices.graphqlserver.model.payloads.header;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * HeaderError
 */
@Getter
@Setter
@ToString
public class HeaderError implements Serializable {
    @JsonProperty("header")
    private String header;

    @JsonProperty("error")
    private String error;
}

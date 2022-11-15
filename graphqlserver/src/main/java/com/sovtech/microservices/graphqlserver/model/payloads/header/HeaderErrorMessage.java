package com.sovtech.microservices.graphqlserver.model.payloads.header;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * HeaderErrorMessage
 */
@Getter
@Setter
@ToString
public class HeaderErrorMessage implements Serializable {
    @JsonProperty("missingHeaders")
    private boolean missingHeaders;

    @JsonProperty("invalidHeaders")
    private List<HeaderError> invalidHeaderErrors;

    /**
     * Instantiates a new Header error message.
     */
    public HeaderErrorMessage() {
        // Empty constructor
        invalidHeaderErrors = new ArrayList<>();
    }
}

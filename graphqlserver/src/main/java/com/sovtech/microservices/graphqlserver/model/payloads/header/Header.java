
package com.sovtech.microservices.graphqlserver.model.payloads.header;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * The type Header.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "requestRefId",
        "responseCode",
        "responseMessage",
        "customerMessage",
        "timestamp"
})
public class Header implements Serializable {
    @JsonProperty("requestRefId")
    private String requestRefId;
    @JsonProperty("responseCode")
    private String responseCode;
    @JsonProperty("responseMessage")
    private String responseMessage;
    @JsonProperty("customerMessage")
    private String customerMessage;
    @JsonProperty("timestamp")
    private String timestamp;
}

package com.sovtech.microservices.graphqlserver.component.beans;


import com.sovtech.microservices.graphqlserver.config.ConfigProperties;
import com.sovtech.microservices.graphqlserver.model.payloads.header.HeaderError;
import com.sovtech.microservices.graphqlserver.model.payloads.header.HeaderErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.sovtech.microservices.graphqlserver.utils.Utilities.httpHeaderValue;
import static com.sovtech.microservices.graphqlserver.utils.Utilities.validateContent;

;


/**
 * The type Data management bean.
 */
@Component
public class DataManagementBean {
    private ConfigProperties configProperties;

    /**
     * Sets config properties.
     *
      * @param configProperties the config properties
     */
    @Autowired
    public void setConfigProperties(ConfigProperties configProperties) {
        this.configProperties = configProperties;
    }

    /**
     * Validate headers mono.
     *
     * @param httpHeaders the http headers
     * @return the mono
     */
    public Mono<HeaderErrorMessage> validateHeaders(HttpHeaders httpHeaders) {
        HeaderErrorMessage headerErrorMessage = new HeaderErrorMessage();
        List<String> requiredHeaders = this.configProperties.getRequiredHeaders();
        Map<String, String> headersToValidateMap = this.configProperties.getHeaderValidationRegexp();

        requiredHeaders.forEach(header -> {
            if (!httpHeaders.containsKey(header)) {
                // Required headers
                headerErrorMessage.setMissingHeaders(true);
            }
        });

        headersToValidateMap.keySet().stream().filter(key -> !validateContent(headersToValidateMap.get(key), httpHeaderValue(httpHeaders, key))).forEach(key -> {
            HeaderError headerError = new HeaderError();
            headerError.setHeader(key);
            headerError.setError("Invalid Header pattern for ".concat(key));
            headerErrorMessage.getInvalidHeaderErrors().add(headerError);
        });
        return Mono.just(headerErrorMessage);
    }

    /**
     * Check if is approved source system boolean.
     *
     * @param xSourceSystem the x source system
     * @return the boolean
     */
    public Boolean checkIfIsApprovedSourceSystem(String xSourceSystem) {
        return this.configProperties.getApprovedSourceSystems().contains(xSourceSystem.toLowerCase(Locale.ROOT));
    }
}

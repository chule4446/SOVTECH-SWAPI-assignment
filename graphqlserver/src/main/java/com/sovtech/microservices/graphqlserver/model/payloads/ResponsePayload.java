package com.sovtech.microservices.graphqlserver.model.payloads;

import java.sql.Timestamp;

/**
 * The type Response payload.
 *
 * @author francis Extends the Model resonse. You can change this object to customize your responses further.
 */
public class ResponsePayload extends ModelApiResponse {
    /**
     * Format the response Object according to the standard. That is {@code} ModelApiResponse
     *
     * @param responseCode    Contains the response code
     * @param referenceId     the reference id
     * @param message         Holds the message of the header
     * @param customerMessage Holds the description of the header
     * @param responseObject  Holds the response Object if available.
     * @return responseFormatter of type ResponseBalances
     */
    public static ResponsePayload responseFormatter(
            String responseCode, String referenceId, String message, String customerMessage, Object responseObject
    ) {
        ResponsePayload responsePayload = new ResponsePayload();
        responsePayload.getApiHeaderResponse().setResponseCode(responseCode);
        responsePayload.getApiHeaderResponse().setRequestRefId(referenceId);
        responsePayload.getApiHeaderResponse().setCustomerMessage(customerMessage);
        responsePayload.getApiHeaderResponse().setResponseMessage(message);
        responsePayload.getApiHeaderResponse().setTimestamp(new Timestamp(System.currentTimeMillis()).toString());
        if (responseObject != null) {
            responsePayload.setResponseBodyObject(responseObject);
        }
        return responsePayload;
    }
}

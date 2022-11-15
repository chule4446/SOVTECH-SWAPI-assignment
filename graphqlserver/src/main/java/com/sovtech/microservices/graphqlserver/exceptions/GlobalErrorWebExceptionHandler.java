package com.sovtech.microservices.graphqlserver.exceptions;

import com.sovtech.microservices.graphqlserver.model.payloads.ResponsePayload;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;

import static com.sovtech.microservices.graphqlserver.utils.GlobalVariables.*;
import static com.sovtech.microservices.graphqlserver.utils.LogsManager.error;
import static com.sovtech.microservices.graphqlserver.utils.Utilities.*;

;


/**
 * The type Global error web exception handler.
 */
@Component
@Order(-2)
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {

    /**
     * Instantiates a new Global error web exception handler.
     *
     * @param globalErrorAttributesSuper                     the g
     * @param applicationContext    the application context
     * @param serverCodecConfigurer the server codec configurer
     */
    public GlobalErrorWebExceptionHandler(ErrorAttributes globalErrorAttributes, ApplicationContext applicationContext,
                                          ServerCodecConfigurer serverCodecConfigurer) {
        super(globalErrorAttributes, new WebProperties.Resources(), applicationContext);
        super.setMessageWriters(serverCodecConfigurer.getWriters());
        super.setMessageReaders(serverCodecConfigurer.getReaders());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(final ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    /**
     * {
     *   "header": {
     *     "requestRefId": "pi0GxSwGjmVKwiTH9dPP3gQ0",
     *     "responseCode": 500,
     *     "responseMessage": "",
     *     "customerMessage": "The transaction could not be processed at this time.",
     *     "timestamp": "2020-09-22 09:07:04.73"
     *   },
     *   "body": " System Error "
     * }
     */
    private Mono<ServerResponse> renderErrorResponse(final ServerRequest request) {
        final Map<String, Object> errorPropertiesMap = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        int statusCode = Integer.parseInt(
                String.valueOf(
                        errorPropertiesMap.getOrDefault("status", RESPONSE_CODE_500)));
        String requestReferenceID = String.valueOf(errorPropertiesMap.getOrDefault("requestId", ""));
        String responseMessage = String.valueOf(errorPropertiesMap.getOrDefault("error", ""));
        String errorBody = String.valueOf(errorPropertiesMap.getOrDefault("error", ""));


        String channel = request.headers().asHttpHeaders().containsKey(X_SOURCE_SYSTEM)
                ? httpHeaderValue(request.headers().asHttpHeaders(), X_SOURCE_SYSTEM) : "";

        ResponsePayload responsePayload = ResponsePayload.responseFormatter(String.valueOf(statusCode),
                requestReferenceID, responseMessage, CUSTOM_CUSTOMER_FAILURE_MESSAGE, errorBody);
        error(requestReferenceID, "swapi Service Global Exception handler", "renderErrorResponse()",
                "",
                channel, TARGET_SYSTEM, responseMessage, statusCode, errorBody,
                errorBody, "",
                parseToJsonString(responsePayload), "HEADERS: = ".concat(logHeaders(request.headers().asHttpHeaders())));
        return ServerResponse.status(statusCode)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(responsePayload));
    }
}

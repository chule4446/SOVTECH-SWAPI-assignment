package com.sovtech.microservices.graphqlserver.component;

import com.sovtech.microservices.graphqlserver.component.beans.DataManagementBean;
import com.sovtech.microservices.graphqlserver.model.payloads.ResponsePayload;
import com.sovtech.microservices.graphqlserver.utils.GlobalVariables;
import com.sovtech.microservices.graphqlserver.utils.Utilities;
import graphql.ExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.Map;

import static com.sovtech.microservices.graphqlserver.utils.GlobalVariables.*;
import static com.sovtech.microservices.graphqlserver.utils.LogsManager.error;
import static com.sovtech.microservices.graphqlserver.utils.LogsManager.info;
import static com.sovtech.microservices.graphqlserver.utils.Utilities.*;

;

@Component
public class ServiceUtility {
    private DataManagementBean dataManagementBean;
    @Autowired
    private final RestTemplate restTemplate;
    @Autowired
    private final GraphQLProvider provider;
    //    WebClient webClient = WebClient.builder().build();
    private final String url = "https://countries.trevorblades.com/";
    @Value("${swapi.dev.url}")
    private String SWAPI_URL;
    protected String appProfile = "development";

    public ServiceUtility(RestTemplate restTemplate, GraphQLProvider provider) {
        this.restTemplate = restTemplate;
        this.provider = provider;
    }


    @Autowired
    public void setDataManagementBean(DataManagementBean dataManagementBean) {
        this.dataManagementBean = dataManagementBean;
    }

    protected Mono<ResponseEntity<ResponsePayload>> validateRequestHeaders(String requestReferenceID, String xSourceSystem,
                                                                           HttpHeaders httpHeaders) {
        return this.dataManagementBean.validateHeaders(httpHeaders).flatMap(headerErrorMessage -> {
            if (headerErrorMessage.isMissingHeaders() || !headerErrorMessage.getInvalidHeaderErrors().isEmpty()) {
                return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponsePayload
                                .responseFormatter(String.valueOf(HttpStatus.BAD_REQUEST.value()), requestReferenceID,
                                        GlobalVariables.MISSING_OR_INCORRECT_PARAMETER, GlobalVariables.MISSING_OR_INCORRECT_PARAMETER, headerErrorMessage)));
            }
            boolean isApprovedSourceSystem = this.dataManagementBean.checkIfIsApprovedSourceSystem(xSourceSystem);
            if (!isApprovedSourceSystem) {
                return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ResponsePayload
                                .responseFormatter(String.valueOf(HttpStatus.UNAUTHORIZED.value()), requestReferenceID,
                                        String.format(GlobalVariables.X_SOURCE_SYSTEM_NOT_AUTHORIZED, xSourceSystem),
                                        String.format(GlobalVariables.X_SOURCE_SYSTEM_NOT_AUTHORIZED, xSourceSystem), "{}")));
            }
            return Mono.empty();
        });
    }

    public Mono<ResponseEntity<ResponsePayload>> processRequest(HttpHeaders httpHeaders,
                                                                String query)  {
        var requestReferenceId = httpHeaderValue(httpHeaders, GlobalVariables.X_CORRELATION_CONVERSATION_ID);
        requestReferenceId = requestReferenceId.length() == 0 ? Utilities.generateRandomString(24) : requestReferenceId;
        var xSourceSystem = httpHeaderValue(httpHeaders, GlobalVariables.X_SOURCE_SYSTEM);
        String finalRequestReferenceId = requestReferenceId;

        try {
            ExecutionResult execute = provider.initiateGraphQL().execute(query);
            Map<String, String> obj = execute.getData();
            info(this.appProfile, requestReferenceId, HEADER_VALIDATION, "processRequest()", "0",
                    "", xSourceSystem, TARGET_SYSTEM, "HEADER_VALIDATION_CHECK", "0",
                    "HEADER_VALIDATION_CHECK", "", "{}", "{}",
                    logHeaders(httpHeaders));
            System.out.println("OBJECT RETURNED" + parseToJsonString(obj));
            return this.validateRequestHeaders(requestReferenceId, xSourceSystem, httpHeaders)
                    .switchIfEmpty(Mono.defer(() -> Mono.justOrEmpty(ResponseEntity.ok(ResponsePayload.responseFormatter("200", finalRequestReferenceId,
                            SUCCESS, SUCCESS, obj)))));
        }catch (Exception ex){
            error(requestReferenceId, "", "sendNotification", "",
                     X_SOURCE_SYSTEM,
                    TARGET_SYSTEM, CUSTOM_CUSTOMER_FAILURE_MESSAGE, 404,
                    "", parseToJsonString(ex), query, "{}","");
            return Mono.justOrEmpty(
                    ResponseEntity
                            .internalServerError()
                            .body(ResponsePayload
                                    .responseFormatter("500",requestReferenceId,
                                            CUSTOM_CUSTOMER_FAILURE_MESSAGE, CUSTOM_CUSTOMER_FAILURE_MESSAGE, "{}")));

        }

    }

}


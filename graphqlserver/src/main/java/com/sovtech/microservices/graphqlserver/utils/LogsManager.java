package com.sovtech.microservices.graphqlserver.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.sovtech.microservices.graphqlserver.utils.GlobalVariables.LOGGER_FORMAT;
import static com.sovtech.microservices.graphqlserver.utils.GlobalVariables.LOG_PROFILE;


/**
 * The type Logs mgr.
 *
 * @author FRANCIS <p> This class helps standardize the logging format in the system.
 */
@SuppressWarnings("squid:S00107")
public class LogsManager {
    private static
    final Logger logger = LoggerFactory.getLogger(LogsManager.class);

    private LogsManager() {
        // Empty private constructor
    }

    /**
     * Info.
     *
     * @param appProfile       the app profile
     * @param requestId        the request id
     * @param transactionType  the transaction type
     * @param process          the process
     * @param processDuration  the process duration
     * @param msisdn           the msisdn
     * @param sourceSystem     the source system
     * @param targetSystem     the target system
     * @param response         the response
     * @param responseCode     the response code
     * @param responseMsg      the response msg
     * @param errorDescription the error description
     * @param requestPayload   the request payload
     * @param responsePayload  the response payload
     * @param headers          the headers
     */
    public static void info(String appProfile, String requestId, String transactionType,
                            String process, String processDuration,
                            String msisdn, String sourceSystem,
                            String targetSystem, String response,
                            String responseCode, String responseMsg,
                            String errorDescription, String requestPayload,
                            String responsePayload, String headers) {
        logger.info(LOGGER_FORMAT,
                requestId, transactionType, process, processDuration,
                msisdn, sourceSystem, targetSystem, response, responseCode,
                responseMsg, errorDescription, LOG_PROFILE.equalsIgnoreCase(appProfile) ? Utilities.parseToJsonString(requestPayload) : "{}",
                LOG_PROFILE.equalsIgnoreCase(appProfile) ? Utilities.parseToJsonString(responsePayload) : "{}", headers);
    }

    /**
     * Error.
     *
     * @param requestId        the request id
     * @param transactionType  the transaction type
     * @param process          the process
     * @param processDuration  the process duration
     * @param msisdn           the msisdn
     * @param sourceSystem     the source system
     * @param targetSystem     the target system
     * @param response         the response
     * @param responseCode     the response code
     * @param responseMsg      the response msg
     * @param errorDescription the error description
     * @param requestPayload   the request payload
     * @param responsePayload  the response payload
     * @param httpHeaders      the http headers
     */
    public static void error(String requestId, String transactionType,
                             String process, String processDuration,
                              String sourceSystem,
                             String targetSystem, String response,
                             int responseCode, String responseMsg,
                             String errorDescription, String requestPayload,
                             String responsePayload, String httpHeaders) {
        logger.error(
                LOGGER_FORMAT,
                requestId, transactionType, process, processDuration,sourceSystem, targetSystem, response, responseCode,
                responseMsg, errorDescription, requestPayload, responsePayload, httpHeaders);
    }
}

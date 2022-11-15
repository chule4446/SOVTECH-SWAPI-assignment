package com.sovtech.microservices.graphqlserver.utils;


/**
 * The type Global variables.
 */
public class GlobalVariables {


    /**
     * The constant LOG_PROFILE.
     */
    public static final String LOG_PROFILE = "development";

    /**
     * The Logger format.
     */
    static final String LOGGER_FORMAT = "TransactionID={} | TransactionType={} | Process={} | ProcessDuration={} | Msisdn={} | SourceSystem={} | TargetSystem={}  | Response={} | ResponseCode={}  | ResponseMsg={} | ErrorDescription={} | RequestPayload={} | ResponsePayload={} | RequestHeaders={}";

    /**
     * The constant X_SOURCE_SYSTEM.
     */
    public static final String X_SOURCE_SYSTEM = "SOVTECH-swapi-portal";

    /**
     * The constant RESPONSE_CODE_500.
     */
    /*================================================
     * RESPONSE CODES
     * ==============================================*/
    public static final int RESPONSE_CODE_500 = 500;

    /**
     * The constant CUSTOM_CUSTOMER_FAILURE_MESSAGE.
     */
    public static final String CUSTOM_CUSTOMER_FAILURE_MESSAGE = "The Request could not be processed at this time.";

    /**
     * The constant SUCCESS_CUSTOMER_MESSAGE.
     */
    public static final String SUCCESS_CUSTOMER_MESSAGE = "Successful request processing";
    /**
     * The constant X_CORRELATION_CONVERSATION_ID.
     */
    public static final String X_CORRELATION_CONVERSATION_ID = "X-Correlation-ConversationID";
    /**
     * The constant X_MSISDN.
     */
    public static final String X_MSISDN = "X-MSISDN";

    /**
     * The constant SUCCESS.
     */
    public static final String SUCCESS = "SUCCESS";




    /**
     * The Header Validation BAD.
     */
    public static final String HEADER_VALIDATION = "Headers validation: graphql-server service";

    /**
     * The constant TARGET_SYSTEM.
     */
    public static final String TARGET_SYSTEM = "SWAPI -service";

    /**
     * The constant MISSING_OR_INCORRECT_PARAMETER.
     */
    public static final String MISSING_OR_INCORRECT_PARAMETER = "Missing or incorrect parameter";

    /**
     * The constant X_SOURCE_SYSTEM_NOT_AUTHORIZED.
     */
    public static final String X_SOURCE_SYSTEM_NOT_AUTHORIZED = "The source system %s is not authorized to use this service.";

    private GlobalVariables() {
    }
}

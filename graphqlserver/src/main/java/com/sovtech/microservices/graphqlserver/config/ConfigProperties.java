package com.sovtech.microservices.graphqlserver.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * The type Config properties.
 */
@Getter
@Setter
@Component
@ConfigurationProperties
public class ConfigProperties {

    private static final String appVersion ="1.0";
    private final AtomicReference<String> artifactId = new AtomicReference<>();
    private String staticIv;
    private String userCredentials;
    private String basicUsername;
    private String basicPassword;
    private String requiredHeaders;
    private String headerValidationRegexp;
    private String approvedSourceSystems;

    /*Datasource configs*/
    private String dataSourceSetDriverClassName;
    private String dataSourceSetJdbcUrl;
    private String dataSourceSetUsername;
    private String dataSourceSetPassword;
    private String dataSourceSetMaximumPoolSize;
    private String dataSourceSetConnectionTestQuery;
    private String dataSourceSetMinimumIdle;
    private String dataSourceSetMaxLifetime;
    private String dataSourceSetAutoCommit;
    private String dataSourceSetConnectionTimeout;
    private String dataSourceSetIdleTimeout;
//    private String dataSourceSetDialect;
    private String datasourceSetTestJpa;
    private String datasourceSetShowSql;

   // @Value("${spring.profiles.active}")
    private String appProfile= "development";



    /**
     * Gets user credentials.
     *
     * @return the user credentials
     */
    public List<String> getUserCredentials() {
        return Arrays.asList(this.userCredentials.split(","));
    }

    /**
     * Gets header validation regexp.
     *
     * @return the header validation regexp
     */
    public Map<String, String> getHeaderValidationRegexp() {
        String[] splitHeadersToValidate = this.headerValidationRegexp.split(";");
        List<String> listHeadersToValidate = Arrays.asList(splitHeadersToValidate);
        return listHeadersToValidate.stream()
                .map(item -> item.split("="))
                .filter(splitHeader -> splitHeader.length == 2)
                .collect(Collectors.toMap(splitHeader -> splitHeader[0], splitHeader -> splitHeader[1], (a, b) -> b, HashMap::new));
    }

    /**
     * Gets required headers.
     *
     * @return the required headers
     */
    public List<String> getRequiredHeaders() {
        String[] splitHeaders = this.requiredHeaders.split(",");
        return Arrays.asList(splitHeaders);
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

package com.sovtech.microservices.graphqlserver.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The type Utilities.
 */
public class Utilities {
    private Utilities() {
    }

    /**
     * Parse to json string string.
     *
     * @param object the object
     * @return the string
     */
    public static String parseToJsonString(Object object) {
        var objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            return "";
        }
    }

    /**
     * Generate random string string.
     *
     * @param length the length
     * @return the string
     */
    public static String generateRandomString(int length) {
        var random = new SecureRandom();
        var leftLimit = 48; // numeral '0'
        var rightLimit = 122; // letter 'z'

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    /**
     * Validate content boolean.
     *
     * @param regex  the regex
     * @param header the header
     * @return the boolean
     */
    public static boolean validateContent(String regex, String header) {
        var pattern = Pattern.compile(regex);
        var matcher = pattern.matcher(header);
        return matcher.matches();
    }

    /**
     * Http header value string.
     *
     * @param httpHeaders the http headers
     * @param key         the key
     * @return the string
     */
    public static String httpHeaderValue(HttpHeaders httpHeaders, String key){
        Optional<List<String>> optionalS = Optional.ofNullable(httpHeaders.get(key));
        if (optionalS.isPresent()){
            Optional<String> optionalS1 = Optional.ofNullable(optionalS.get().get(0));
            return optionalS1.orElse("");
        }
        return "";
    }

    /**
     * Log headers string.
     *
     * @param httpHeaders the http headers
     * @return the string
     */
    public static String logHeaders(HttpHeaders httpHeaders) {
        List<String> badHeaders = new ArrayList<>();
        badHeaders.add("authorization");
        badHeaders.add("postman-token");
        badHeaders.add("X-MessageID");
        badHeaders.add("X-DeviceInfo");
        badHeaders.add("X-DeviceId");
        badHeaders.add("X-DeviceToken");
        badHeaders.add("Authorization");

        return httpHeaders
                .keySet()
                .stream()
                .filter(header -> !badHeaders.contains(header))
                .map(header -> header + " : " + httpHeaderValue(httpHeaders, header) + System.lineSeparator())
                .collect(Collectors.joining());
    }
    public static String getSchemaFromFileName(final String filename) throws IOException {
        System.out.println("File name++++ " + filename);
        return new String(
                Utilities.class.getClassLoader().getResourceAsStream("graphql/" +
                        filename + ".graphql").readAllBytes());

    }
}

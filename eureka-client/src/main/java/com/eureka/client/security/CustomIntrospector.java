package com.eureka.client.security;

import com.eureka.client.exception.URIException;
import com.eureka.client.model.Token;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.server.resource.introspection.SpringOpaqueTokenIntrospector;
import org.springframework.web.client.RestOperations;

import java.net.URI;
import java.net.URISyntaxException;

public class CustomIntrospector extends SpringOpaqueTokenIntrospector {
    private final String introspectionUri;

    public CustomIntrospector(String introspectionUri, String clientId, String clientSecret) {
        super(introspectionUri, clientId, clientSecret);
        super.setRequestEntityConverter(defaultRequestEntityConverter());
        this.introspectionUri = introspectionUri;
    }


    public CustomIntrospector(String introspectionUri, RestOperations restOperations) {
        super(introspectionUri, restOperations);
        this.introspectionUri = introspectionUri;
    }

    private Converter<String, RequestEntity<?>> defaultRequestEntityConverter() {
        return token -> {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-type",MediaType.APPLICATION_JSON.toString());
            Token body = new Token();
            body.setToken(token);
            URI uri = null;
            try {
                uri = new URI(introspectionUri);
            } catch (URISyntaxException e) {
                throw new URIException("Problem with uri happened.",e);
            }
            return new RequestEntity<>(body, headers, HttpMethod.POST, uri);
        };
    }
}

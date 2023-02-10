package com.eureka.client.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static java.util.Arrays.stream;

@Component
@Slf4j
public class CustomTokenIntrospector implements OpaqueTokenIntrospector {
    private final OpaqueTokenIntrospector delegate;

    public CustomTokenIntrospector() {
        this.delegate = new CustomIntrospector(
                "http://host.docker.internal:8085/auth/authenticated",
                "client-id",
                "client-secret");
    }

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        OAuth2AuthenticatedPrincipal principal = this.delegate.introspect(token);
        String username = principal.getAttribute("name");
        Collection<GrantedAuthority> authorities = principal.getAuthorities().stream().map(GrantedAuthority.class::cast).toList();
        return new OAuth2IntrospectionAuthenticatedPrincipal(username, principal.getAttributes(), authorities);
    }

    public static String getAccessTokenCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies.length > 0) {
            String bearer = stream(cookies)
                    .map(Cookie::getName)
                    .map(String::trim)
                    .filter(c -> c.startsWith("Bearer_"))
                    .findFirst().orElse(null);
            if (bearer != null) {
                return bearer.replace("Bearer_", Strings.EMPTY);
            }
        }
        return null;
    }
}

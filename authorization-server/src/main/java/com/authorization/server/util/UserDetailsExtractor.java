package com.authorization.server.util;

import com.authorization.server.config.JWTService;
import com.authorization.server.dto.OAuth2UserDTO;
import com.authorization.server.model.User;
import com.authorization.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2TokenIntrospectionClaimNames;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDetailsExtractor {
    private final JWTService jwtService;
    private final UserRepository userRepository;

    public OAuth2AuthenticatedPrincipal extractDetails(String token) {
        try {
            String username = jwtService.extractUsername(token);
            if (username != null) {
                UserDetails user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException(username + " not found"));
                if (jwtService.isTokenValid(token, user)) {
                    Map<String, Object> attributes = new HashMap<>();
                    attributes.put("user_name", user.getUsername());
                    attributes.put("authorities", user.getAuthorities());
                    attributes.put(OAuth2TokenIntrospectionClaimNames.ACTIVE, Boolean.TRUE);
                    return new OAuth2UserDTO(attributes, user.getAuthorities(), username, true, createScope(user.getAuthorities()));
                }
            }
        } catch (Exception ex) {
            log.warn("user authentication check failed.");
        }
        return null;
    }

    public String createScope(Collection<? extends GrantedAuthority> authorities) {
        if (authorities == null || authorities.isEmpty()) {
            return Strings.EMPTY;
        }
        Optional<String> reduce = authorities.stream().map(GrantedAuthority::getAuthority).reduce((a, b) -> a + " " + b);
        return reduce.orElse(Strings.EMPTY);
    }
}

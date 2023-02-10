package com.eureka.client.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ResourceServerConfiguration {
    private final CustomTokenIntrospector customTokenIntrospector;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()

                .authorizeHttpRequests(requests ->
                        requests
//                                .anyRequest()
//                                .authenticated()
                                .requestMatchers(new AntPathRequestMatcher("/main/test"))
                                .permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/main/translate"))
                                .permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/main/secured"))
                                .hasAuthority("SCOPE_USER")
                                .and())
                .oauth2ResourceServer(oauth2ResourceServer ->
                        oauth2ResourceServer
                                .opaqueToken(opaqueToken ->
                                        opaqueToken.introspector(customTokenIntrospector))
                                .bearerTokenResolver(CustomTokenIntrospector::getAccessTokenCookie).and())
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
}

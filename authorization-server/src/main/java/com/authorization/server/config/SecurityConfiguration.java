package com.authorization.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

//
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()
                .authorizeHttpRequests()
                .anyRequest().permitAll();
        return http.build();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((authorize) -> authorize
//                        .antMatchers("/api/auth/*").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .csrf().disable()
//                .cors().disable()
//                .httpBasic().disable()
//                .oauth2ResourceServer((oauth2) ->
//                        oauth2.jwt((jwt) -> jwt.jwtAuthenticationConverter(jwtToUserConverter))
//                )
//                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .exceptionHandling((exceptions) -> exceptions
//                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
//                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
//                );
//        return http.build();
//    }
//
//    @Bean
//    @Primary
//    JwtDecoder jwtAccessTokenDecoder() {
//        return NimbusJwtDecoder.withPublicKey(keyUtils.getAccessTokenPublicKey()).build();
//    }
//
//    @Bean
//    @Primary
//    JwtEncoder jwtAccessTokenEncoder() {
//        JWK jwk = new RSAKey
//                .Builder(keyUtils.getAccessTokenPublicKey())
//                .privateKey(keyUtils.getAccessTokenPrivateKey())
//                .build();
//        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
//        return new NimbusJwtEncoder(jwks);
//    }
//
//    @Bean
//    @Qualifier("jwtRefreshTokenDecoder")
//    JwtDecoder jwtRefreshTokenDecoder() {
//        return NimbusJwtDecoder.withPublicKey(keyUtils.getRefreshTokenPublicKey()).build();
//    }
//
//    @Bean
//    @Qualifier("jwtRefreshTokenEncoder")
//    JwtEncoder jwtRefreshTokenEncoder() {
//        JWK jwk = new RSAKey
//                .Builder(keyUtils.getRefreshTokenPublicKey())
//                .privateKey(keyUtils.getRefreshTokenPrivateKey())
//                .build();
//        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
//        return new NimbusJwtEncoder(jwks);
//    }
//
//    @Bean
//    @Qualifier("jwtRefreshTokenAuthProvider")
//    JwtAuthenticationProvider jwtRefreshTokenAuthProvider() {
//        JwtAuthenticationProvider provider = new JwtAuthenticationProvider(jwtRefreshTokenDecoder());
//        provider.setJwtAuthenticationConverter(jwtToUserConverter);
//        return provider;
//    }
//
//    @Bean
//    DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setPasswordEncoder(passwordEncoder);
//        provider.setUserDetailsService(userDetailsManager);
//        return provider;
//    }
}
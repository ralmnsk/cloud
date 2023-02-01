package com.authorization.server.controller;

import com.authorization.server.dto.AuthenticationResponse;
import com.authorization.server.dto.Login;
import com.authorization.server.dto.OAuth2UserDTO;
import com.authorization.server.dto.Registration;
import com.authorization.server.dto.Token;
import com.authorization.server.service.AuthenticationService;
import com.authorization.server.util.UserDetailsExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserDetailsExtractor userDetailsExtractor;

    @PostMapping(value = "/register", consumes = {"application/json"})
    public AuthenticationResponse register(@RequestBody Registration registration) {
        return authenticationService.register(registration);
    }

    @PostMapping(value = "/login", consumes = {"application/json"})
    public AuthenticationResponse login(@RequestBody Login login) {
        return authenticationService.authenticate(login);
    }

    @PostMapping(value = "/authenticated", consumes = {"application/json"})
    public ResponseEntity<OAuth2AuthenticatedPrincipal> isAuthenticated(@RequestBody Token token) {
        OAuth2AuthenticatedPrincipal user = userDetailsExtractor.extractDetails(token.getToken());
        if (user != null) {
            return new ResponseEntity(user, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
}

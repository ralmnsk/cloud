package com.authorization.server.service;

import com.authorization.server.config.JWTService;
import com.authorization.server.dto.AuthenticationResponse;
import com.authorization.server.dto.Login;
import com.authorization.server.dto.Registration;
import com.authorization.server.model.Role;
import com.authorization.server.model.User;
import com.authorization.server.repository.UserRepository;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final Mapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(Registration registration) {
        User user = mapper.map(registration, User.class);
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        user.setRoles(List.of(Role.USER));
        userRepository.save(user);

        return new AuthenticationResponse(jwtService.generateToken(user));
    }

    public AuthenticationResponse authenticate(Login login) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getLogin(),
                        login.getPassword())
        );
        var user = userRepository.findByEmail(login.getLogin()).orElseThrow();
        return new AuthenticationResponse(jwtService.generateToken(user));
    }
}

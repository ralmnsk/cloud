package com.authorization.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class AuthenticationResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 7613907591539040576L;
    private String token;
}

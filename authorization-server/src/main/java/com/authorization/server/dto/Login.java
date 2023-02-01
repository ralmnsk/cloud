package com.authorization.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Login implements Serializable {
    private static final long serialVersionUID = -808265757716776000L;
    private String login;
    private String password;
}

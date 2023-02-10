package com.eureka.client.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class Translated implements Serializable {
    private static final long serialVersionUID = 1565026461712360408L;
    private String message;
}

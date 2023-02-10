package com.eureka.client.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class ToTranslate implements Serializable {
    private static final long serialVersionUID = -6323921210111479184L;
    private String message;
    private String from;
    private String to;
}

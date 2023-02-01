package com.eureka.client.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/main")
public class MainController {
    @Value("${eureka.instance.instance-id}")
    private String value;

    @GetMapping("/test")
    public String testing() {
        return "Hello world," + value;
    }

    @GetMapping("/secured")
    public String secured(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal userPrinciple) {
        return "Secured information from instance-id:" + value;
    }
}

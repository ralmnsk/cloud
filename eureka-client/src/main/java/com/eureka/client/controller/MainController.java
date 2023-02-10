package com.eureka.client.controller;

import com.eureka.client.dto.ToTranslate;
import com.eureka.client.dto.Translated;
import com.eureka.client.service.TranslateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/main")
public class MainController {
    @Value("${eureka.instance.instance-id}")
    private String value;
    private TranslateService translateService;

    @Autowired
    public MainController(TranslateService translateService) {
        this.translateService = translateService;
    }

    @GetMapping("/test")
    public String testing() {
        return "Hello world," + value;
    }

    @PostMapping("/translate")
    public Translated translate(@RequestBody ToTranslate request) {
        return translateService.translate(request);
    }

    @GetMapping("/secured")
    public String secured(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal userPrinciple) {
        return "Secured information from instance-id:" + value;
    }
}

package com.oauth2.app.oauth2_authorization_client.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/secure")
public class SecureApiController {

    @GetMapping(value = "/ping")
    public String ping(){
        return "PONG !!";
    }
}

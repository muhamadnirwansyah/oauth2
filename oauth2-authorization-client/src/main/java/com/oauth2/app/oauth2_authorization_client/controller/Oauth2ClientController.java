package com.oauth2.app.oauth2_authorization_client.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class Oauth2ClientController {

    @GetMapping
    public String home() {
        return "Welcome! Go to <a href='/client/protected'>Protected Page</a>";
    }

    @GetMapping("/protected")
    public String protectedPage(OAuth2AuthenticationToken token) {
        return "Logged in as: " + token.getPrincipal().getName();
    }
}

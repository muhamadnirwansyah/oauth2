package com.oauth2.app.oauth2_authorization_client.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/oauth2/code")
public class Oauth2CallbackClientController {

    @GetMapping("/angular-client")
    public ResponseEntity<String> clientCode(@RequestParam("code")String code) {
        return ResponseEntity.ok("Success code "+code);
    }
}

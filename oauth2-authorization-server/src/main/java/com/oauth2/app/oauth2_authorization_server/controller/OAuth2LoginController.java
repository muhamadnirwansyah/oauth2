package com.oauth2.app.oauth2_authorization_server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OAuth2LoginController {

    @GetMapping(value = "/login")
    public String customLoginPageOauth2(){
        return "login-oauth2";
    }

}

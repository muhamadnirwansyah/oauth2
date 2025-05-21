package com.oauth2.app.oauth2_authorization_server.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OAuth2LoginController {

    @GetMapping(value = "/login")
    public String customLoginPageOauth2(){
        return "login-oauth2";
    }

    @GetMapping(value = "/dashboard")
    public String dashboardPage(Authentication authentication, Model model){
        model.addAttribute("username", authentication.getName());
        model.addAttribute("authorities", authentication.getAuthorities());
        return "dashboard";
    }
}

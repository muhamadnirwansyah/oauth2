package com.oauth2.app.oauth2_authorization_server.controller;

import com.oauth2.app.oauth2_authorization_server.entity.Roles;
import com.oauth2.app.oauth2_authorization_server.model.OAuth2ClientRegistrationRequest;
import com.oauth2.app.oauth2_authorization_server.model.RolesRequest;
import com.oauth2.app.oauth2_authorization_server.model.RolesResponse;
import com.oauth2.app.oauth2_authorization_server.service.account.OAuth2ClientRegistrationService;
import com.oauth2.app.oauth2_authorization_server.service.roles.RolesService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final RolesService rolesService;
    private final OAuth2ClientRegistrationService oAuth2ClientRegistrationService;

    @GetMapping(value = "/dashboard")
    public String dashboardPage(Authentication authentication, Model model, HttpServletRequest request){
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("username", authentication.getName());
        model.addAttribute("authorities", authentication.getAuthorities());
        return "dashboard";
    }

    @GetMapping(value = "/home")
    public String homePage(HttpServletRequest request, Model model){
        model.addAttribute("currentUri", request.getRequestURI());
        return "home";
    }

    @GetMapping(value = "/account")
    public String accountPage(HttpServletRequest request, Model model){
        model.addAttribute("currentUri", request.getRequestURI());
        return "account";
    }

    @GetMapping(value = "/roles")
    public String rolesPage(HttpServletRequest request, Model model){
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("role", new Roles());
        model.addAttribute("roles", rolesService.fetchRoles());
        return "roles";
    }

    @PostMapping(value = "/save-roles")
    public String saveRoles(@ModelAttribute RolesRequest request, RedirectAttributes redirectAttributes){
        try{
            RolesResponse response = rolesService.save(request);
            String message = "Roles with ID : "+response.getId()+" and name : "+response.getName();
            redirectAttributes.addFlashAttribute("successMessage", message);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error : "+e.getMessage());
        }
        return "redirect:/roles";
    }

    @GetMapping(value = "/delete-role/{id}")
    public String deleteRoles(@PathVariable("id")Long id, RedirectAttributes redirectAttributes){
        try{
            RolesResponse response = rolesService.delete(id);
            String message = "Roles with ID :"+response.getId()+" successfully deleted";
            redirectAttributes.addFlashAttribute("successMessage", message);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error : "+e.getMessage());
        }
        return "redirect:/roles";
    }

    @GetMapping(value = "/client")
    public String clientPage(HttpServletRequest request, Model model){
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("client", new OAuth2ClientRegistrationRequest());
        model.addAttribute("clients", oAuth2ClientRegistrationService.fetchOauth2Clients());
        return "client";
    }
}

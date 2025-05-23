package com.oauth2.app.oauth2_authorization_server.controller;

import com.oauth2.app.oauth2_authorization_server.model.RegisterRequest;
import com.oauth2.app.oauth2_authorization_server.model.RegisterResponse;
import com.oauth2.app.oauth2_authorization_server.service.account.RegisterAccountService;
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
public class PageAccountController {

    private final RegisterAccountService registerAccountService;
    private final RolesService rolesService;

    @GetMapping(value = "/dashboard")
    public String dashboardPage(Authentication authentication, Model model, HttpServletRequest request){
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("username", authentication.getName());
        model.addAttribute("authorities", authentication.getAuthorities());
        return "dashboard";
    }

    @GetMapping(value = "/account")
    public String accountPage(HttpServletRequest request, Model model){
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("account", new RegisterRequest());
        model.addAttribute("roles", rolesService.fetchRoles());
        model.addAttribute("accounts", registerAccountService.fetch());
        return "account";
    }

    @GetMapping(value = "/delete-account/{id}")
    public String delete(@PathVariable("id")Long id, RedirectAttributes redirectAttributes){
        try{
            registerAccountService.delete(id);
            String message = "Account with ID "+id+" has been deleted";
            redirectAttributes.addFlashAttribute("successMessage", message);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/account";
    }

    @PostMapping(value = "/save-account")
    public String save(@ModelAttribute RegisterRequest request, RedirectAttributes redirectAttributes){
        try{
            RegisterResponse response = registerAccountService.register(request);
            String message = "Account with ID "+response.getId()+" is successfully registered";
            redirectAttributes.addFlashAttribute("successMessage",message);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/account";
    }
}

package com.oauth2.app.oauth2_authorization_server.controller;

import com.oauth2.app.oauth2_authorization_server.model.OAuth2ClientRegistrationRequest;
import com.oauth2.app.oauth2_authorization_server.model.OAuth2ClientRegistrationResponse;
import com.oauth2.app.oauth2_authorization_server.service.account.OAuth2ClientRegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class PageClientController {

    private final OAuth2ClientRegistrationService oAuth2ClientRegistrationService;

    @PostMapping(value = "/save-client")
    public String saveClients(@ModelAttribute OAuth2ClientRegistrationRequest request, RedirectAttributes redirectAttributes){
        try{
            OAuth2ClientRegistrationResponse response = oAuth2ClientRegistrationService.save(request);
            String message = "Client with ID : "+response.getClientId();
            redirectAttributes.addFlashAttribute("successMessage",message);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error : "+e.getMessage());
        }
        return "redirect:/client";
    }

    @GetMapping(value = "/delete-client/{id}")
    public String deleteClient(@PathVariable("id")String id, RedirectAttributes redirectAttributes){
        try{
            oAuth2ClientRegistrationService.delete(id);
            String message = "Client with ID : "+id+" successfully deleted";
            redirectAttributes.addFlashAttribute("successMessage", message);
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", "Error : "+e.getMessage());
        }
        return "redirect:/client";
    }

    @GetMapping(value = "/client")
    public String clientPage(HttpServletRequest request, Model model){
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("client", new OAuth2ClientRegistrationRequest());
        model.addAttribute("clients", oAuth2ClientRegistrationService.fetchOauth2Clients());
        return "client";
    }
}

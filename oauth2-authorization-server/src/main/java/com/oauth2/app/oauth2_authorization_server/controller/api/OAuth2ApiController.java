package com.oauth2.app.oauth2_authorization_server.controller.api;

import com.oauth2.app.oauth2_authorization_server.model.OAuth2ClientRegistrationRequest;
import com.oauth2.app.oauth2_authorization_server.model.OAuth2ClientRegistrationResponse;
import com.oauth2.app.oauth2_authorization_server.service.account.OAuth2ClientRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1.0/oauth2")
@RequiredArgsConstructor
public class OAuth2ApiController {

    private final OAuth2ClientRegistrationService oAuth2ClientRegistrationService;

    @PostMapping(value = "/client-registration")
    public ResponseEntity<OAuth2ClientRegistrationResponse> saveOauth2ClientRegistration(@RequestBody OAuth2ClientRegistrationRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(oAuth2ClientRegistrationService.save(request));
    }
}

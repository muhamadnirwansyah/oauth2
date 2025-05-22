package com.oauth2.app.oauth2_authorization_server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OAuth2ClientRegistrationRequest {

    private String id;
    private String clientId;
    private String clientSecret;
    private List<String> scopes;
    private long accessTokenHours;
    private long refreshTokenDays;
    private String redirectUri;
}

package com.oauth2.app.oauth2_authorization_server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private Long id;
    @JsonProperty("account_username")
    private String username;
    @JsonProperty("account_password")
    private String password;
    @JsonProperty("account_email")
    private String email;
    @JsonProperty("account_rolesid")
    private Long rolesId;
}

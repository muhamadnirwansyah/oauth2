package com.oauth2.app.oauth2_authorization_server.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterResponse {

    @JsonProperty("account_id")
    private Long id;
    @JsonProperty("account_username")
    private String username;
    @JsonProperty("account_email")
    private String email;
    @JsonProperty("account_rolesid")
    private Long rolesId;
}

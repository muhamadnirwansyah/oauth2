package com.oauth2.app.oauth2_authorization_server.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountResponse {

    private Long id;
    private String username;
    private String password;
    private String email;
    private Long idRoles;
    private String roles;

}

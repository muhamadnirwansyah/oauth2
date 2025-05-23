package com.oauth2.app.oauth2_authorization_server.model;

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
    private String username;
    private String password;
    private String email;
    private Long rolesId;
}

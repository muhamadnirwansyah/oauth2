package com.oauth2.app.oauth2_authorization_server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RolesRequest {

    private Long id;
    private String name;

}

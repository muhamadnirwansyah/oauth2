package com.oauth2.app.oauth2_authorization_server.service.account;

import com.oauth2.app.oauth2_authorization_server.model.OAuth2ClientRegistrationRequest;
import com.oauth2.app.oauth2_authorization_server.model.OAuth2ClientRegistrationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2ClientRegistrationService {

    private final JdbcTemplate jdbcTemplate;
    private final RegisteredClientRepository registeredClientRepository;
    private final PasswordEncoder passwordEncoder;

    public List<OAuth2ClientRegistrationResponse> fetchOauth2Clients(){
        log.info("process fetch oauth2 clients..");

        String querySql = "SELECT id, client_id, client_secret, client_authentication_methods," +
                "authorization_grant_types, redirect_uris, scopes, client_name FROM oauth2_registered_client";

       RowMapper<OAuth2ClientRegistrationResponse> rowMapper = (rs, rowNum) -> {
           OAuth2ClientRegistrationResponse response = new OAuth2ClientRegistrationResponse();
           response.setId(rs.getString("id"));
           response.setClientId(rs.getString("client_id"));
           response.setClientSecret(rs.getString("client_secret"));
           response.setClientAuthenticationMethod(rs.getString("client_authentication_methods"));
           response.setAuthorizationGrantType(rs.getString("authorization_grant_types"));
           response.setRedirectUri(rs.getString("redirect_uris"));
           response.setScope(rs.getString("scopes"));
           response.setClientName(rs.getString("client_name"));
           return response;
       };
       return jdbcTemplate.query(querySql, rowMapper);
    }

    public void delete(String id){
        log.info("process delete oauth2 with id : {}",id);
        String querySql = "DELETE FROM oauth2_registered_client WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(querySql, id);
        if (rowsAffected == 0){
            throw new IllegalArgumentException("Failed delete oauth2 client registered : "+id+" because id not found");
        }
    }

    public OAuth2ClientRegistrationResponse update(OAuth2ClientRegistrationRequest request){
        log.info("process update oauth2 client registration.");
        RegisteredClient existsRegisteredClient = registeredClientRepository.findByClientId(request.getClientId());
        if (Objects.isNull(existsRegisteredClient)){
            throw new IllegalArgumentException("Client ID not found : "+existsRegisteredClient.getClientId());
        }

        if (request.getAccessTokenHours() == 0 || request.getRefreshTokenDays() == 0){
            throw new IllegalArgumentException("Access Token or Refresh Token Days cannot be 0");
        }

        RegisteredClient updateRegistredClient = RegisteredClient.from(existsRegisteredClient)
                .clientSecret(existsRegisteredClient.getClientSecret()) /** keep client secret **/
                .redirectUris(redirects -> {
                    redirects.clear();
                    redirects.add(request.getRedirectUri());
                })
                .scopes(scopes -> {
                    scopes.clear();
                    scopes.addAll(request.getScopes());
                })
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofHours(request.getAccessTokenHours()))
                        .refreshTokenTimeToLive(Duration.ofDays(request.getRefreshTokenDays())).build())
                .build();

        registeredClientRepository.save(updateRegistredClient);
        return OAuth2ClientRegistrationResponse.builder()
                .clientId(request.getClientId())
                .clientSecret(request.getClientSecret())
                .redirectUri(request.getRedirectUri())
                .accessTokenHours(request.getAccessTokenHours())
                .refreshTokenDays(request.getRefreshTokenDays())
                .scopes(request.getScopes())
                .build();
    }

    public OAuth2ClientRegistrationResponse save(OAuth2ClientRegistrationRequest request){

        if (Objects.isNull(request.getId())){
            log.info("process create oauth2 client registration : {}",request);
            var clientSecretEncode = passwordEncoder.encode(request.getClientSecret());
            RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                    .clientId(request.getClientId())
                    .clientSecret(clientSecretEncode)
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                    .redirectUri(request.getRedirectUri())
                    .scopes(scopRequest -> scopRequest.addAll(request.getScopes()))
                    .tokenSettings(TokenSettings.builder()
                            .accessTokenTimeToLive(Duration.ofHours(request.getAccessTokenHours()))
                            .refreshTokenTimeToLive(Duration.ofDays(request.getRefreshTokenDays())).build()).build();
            registeredClientRepository.save(registeredClient);
            return OAuth2ClientRegistrationResponse.builder()
                    .clientId(request.getClientId())
                    .clientSecret(clientSecretEncode)
                    .redirectUri(request.getRedirectUri())
                    .accessTokenHours(request.getAccessTokenHours())
                    .refreshTokenDays(request.getRefreshTokenDays())
                    .scopes(request.getScopes())
                    .build();
        }
        return update(request);
    }
}

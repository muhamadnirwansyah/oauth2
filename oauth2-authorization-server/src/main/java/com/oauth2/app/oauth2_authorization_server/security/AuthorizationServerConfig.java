package com.oauth2.app.oauth2_authorization_server.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.util.matcher.RequestMatcher;
import java.util.Objects;

@Configuration
public class AuthorizationServerConfig {

    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }

    @Bean
    public OAuth2AuthorizationService authorizationService() {
        return new InMemoryOAuth2AuthorizationService();
    }

    @Bean
    public OAuth2AuthorizationConsentService authorizationConsentService() {
        return new InMemoryOAuth2AuthorizationConsentService();
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

    @SuppressWarnings("removal")
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        OAuth2AuthorizationServerConfigurer auth2AuthorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();

        RequestMatcher endPointMatcher = auth2AuthorizationServerConfigurer.getEndpointsMatcher();
        httpSecurity.securityMatcher(endPointMatcher::matches)
                .authorizeHttpRequests(authorize ->
                        authorize.anyRequest().authenticated())
                .csrf(csrf -> csrf.ignoringRequestMatchers(endPointMatcher))
                .apply(auth2AuthorizationServerConfigurer);

        httpSecurity.formLogin(loginForm -> loginForm.loginPage("/login").permitAll());
        return httpSecurity.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/css/**", "/client", "/account", "/roles").authenticated()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler(customAuthenticationEntryPoint())
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID"));

        return httpSecurity.build();
    }

    /** custom authentication handle if success request from client or not **/
    @Bean
    public AuthenticationSuccessHandler customAuthenticationEntryPoint() {
        return (request, response, authentication) -> {
            SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
            if (!Objects.isNull(savedRequest)){
                /** if request from client will redirect to authentication code **/
                String targetUrl = savedRequest.getRedirectUrl();
                response.sendRedirect(targetUrl);
            }else{
                /** if request from internal will redirect internal dashboard **/
                response.sendRedirect("/dashboard");
            }
        };
    }
}

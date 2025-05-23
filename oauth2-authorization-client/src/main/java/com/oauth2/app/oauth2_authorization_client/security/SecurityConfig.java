package com.oauth2.app.oauth2_authorization_client.security;

import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    public SecurityConfig(CustomAuthenticationEntryPoint customAuthenticationEntryPoint){
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer
                .ignoringRequestMatchers("/api/v1/**"));
       httpSecurity.authorizeHttpRequests(authorize -> authorize
                       .requestMatchers("/api/v1/**").authenticated()
                       .requestMatchers("/oauth2/**", "/login/**", "/error").permitAll()
                       .anyRequest().authenticated())
               .oauth2Login(oauth2 -> oauth2
                       .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint
                               .authorizationRequestRepository(new
                                       HttpSessionOAuth2AuthorizationRequestRepository()))
                       .defaultSuccessUrl("/", true)
                       .failureUrl("/login?error"))
               /** oauth2 resource server config and custom entry point **/
               .oauth2ResourceServer(resourceServer -> resourceServer
                       .jwt(jwtConfigurer -> {})
                       .authenticationEntryPoint(customAuthenticationEntryPoint));
       return httpSecurity.build();
    }

    /** for same site testing **/
    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer(){
        return (factory) -> factory.addContextCustomizers((context) -> {
            final Rfc6265CookieProcessor cookieProcessor = new Rfc6265CookieProcessor();
            cookieProcessor.setSameSiteCookies("None");
            context.setCookieProcessor(cookieProcessor);
        });
    }

}

package com.oauth2.app.oauth2_authorization_server.service.account;

import com.oauth2.app.oauth2_authorization_server.entity.Account;
import com.oauth2.app.oauth2_authorization_server.entity.Roles;
import com.oauth2.app.oauth2_authorization_server.model.RegisterRequest;
import com.oauth2.app.oauth2_authorization_server.model.RegisterResponse;
import com.oauth2.app.oauth2_authorization_server.repository.AccountRepository;
import com.oauth2.app.oauth2_authorization_server.repository.RolesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterAccountService {

    private final AccountRepository accountRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponse register(RegisterRequest request){
        log.info("process create new account : {}",request);
        if (accountRepository.findByUsername(request.getUsername()).isPresent()){
            throw new IllegalArgumentException("Account username is already exist !");
        }
        Roles roles = rolesRepository.findById(request.getRolesId())
                .orElseThrow(() -> new IllegalArgumentException("Roles ID is not found !"));
        Account account = new Account();
        account.setUsername(request.getUsername());
        account.setEmail(request.getEmail());
        account.setRoles(roles);
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        var response = accountRepository.save(account);
        return RegisterResponse.builder()
                .id(response.getId()).email(response.getEmail()).rolesId(response.getRoles().getId())
                .username(response.getUsername())
                .build();
    }
}

package com.oauth2.app.oauth2_authorization_server.service.account;

import com.oauth2.app.oauth2_authorization_server.entity.Account;
import com.oauth2.app.oauth2_authorization_server.entity.Roles;
import com.oauth2.app.oauth2_authorization_server.model.AccountResponse;
import com.oauth2.app.oauth2_authorization_server.model.RegisterRequest;
import com.oauth2.app.oauth2_authorization_server.model.RegisterResponse;
import com.oauth2.app.oauth2_authorization_server.repository.AccountRepository;
import com.oauth2.app.oauth2_authorization_server.repository.RolesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterAccountService {

    private final AccountRepository accountRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponse register(RegisterRequest request){
        if (Objects.isNull(request.getId())){
            log.info("process create new account : {}",request);
            validationAccount(request);
            Account account = new Account();
            account.setUsername(request.getUsername());
            account.setEmail(request.getEmail());
            account.setRoles(roles(request.getRolesId()));
            account.setPassword(passwordEncoder.encode(request.getPassword()));
            var response = accountRepository.save(account);
            return RegisterResponse.builder()
                    .id(response.getId()).email(response.getEmail()).rolesId(response.getRoles().getId())
                    .username(response.getUsername())
                    .build();
        }
        return update(request);
    }

    private void validationAccount(RegisterRequest request){
        if (request.getEmail().trim().isEmpty() ||
                request.getUsername().trim().isEmpty() ||
                request.getPassword().trim().isEmpty() ||
                Objects.isNull(request.getRolesId())) {
            throw new IllegalArgumentException("Hi email, username, roles and password cannot be null !");
        }
        if (accountRepository.findByUsername(request.getUsername()).isPresent()){
            throw new IllegalArgumentException("Account username is already exist !");
        }
    }

    private Roles roles(Long rolesId){
        return rolesRepository.findById(rolesId)
                .orElseThrow(() -> new IllegalArgumentException("Roles ID is not found !"));
    }

    public RegisterResponse update(RegisterRequest request){
        validationAccount(request);
        log.info("process update account : {}",request);
        Account currentAccount = accountRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("Account ID is not found !"));
        currentAccount.setEmail(request.getEmail());
        currentAccount.setPassword(passwordEncoder.encode(request.getPassword()));
        currentAccount.setRoles(roles(request.getRolesId()));
        currentAccount.setUsername(request.getUsername());
        var response = accountRepository.save(currentAccount);
        return RegisterResponse.builder()
                .id(response.getId())
                .username(response.getUsername())
                .rolesId(response.getRoles().getId())
                .email(response.getEmail())
                .username(response.getUsername())
                .build();
    }

    public void delete(Long id){
        log.info("process delete account : {}",id);
        Account currentAccount = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account ID is not found !"));
        accountRepository.delete(currentAccount);
    }

    public List<AccountResponse> fetch(){
        log.info("process fetch list account..");
        return accountRepository.findAll().stream()
                .map(account -> AccountResponse.builder()
                        .id(account.getId())
                        .username(account.getUsername())
                        .idRoles(account.getRoles().getId())
                        .roles(account.getRoles().getName())
                        .email(account.getEmail())
                        .build())
                .toList();
    }
}

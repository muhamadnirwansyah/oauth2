package com.oauth2.app.oauth2_authorization_server.security;

import com.oauth2.app.oauth2_authorization_server.entity.Account;
import com.oauth2.app.oauth2_authorization_server.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AccountDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    public AccountDetailsService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new User(
                account.getUsername(),
                account.getPassword(),
                true,
                true,
                true,
                true,
                List.of(new SimpleGrantedAuthority(account.getRoles().getName()))
        );
    }
}

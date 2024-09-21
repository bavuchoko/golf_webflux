package com.pjs.golf_webflex.app.auth.service;

import com.pjs.golf_webflex.app.auth.adapter.AccountAdapter;
import com.pjs.golf_webflex.app.auth.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements ReactiveUserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return  accountRepository.findByUsername(username)
                .map(account -> (UserDetails) new AccountAdapter(account))
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found")));
    }

}

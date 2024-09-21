package com.pjs.golf_webflex.app.auth.service;

import com.pjs.golf_webflex.app.auth.adapter.AccountAdapter;
import com.pjs.golf_webflex.app.auth.dto.LoginRequestDto;
import com.pjs.golf_webflex.app.auth.repository.AccountRepository;
import com.pjs.golf_webflex.app.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthService implements ReactiveUserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final JwtUtil jwtUtil;


    public Mono<String> authorize(LoginRequestDto loginRequestDto) {
        return this.findByUsername(loginRequestDto.getUsername())
                .flatMap(userDetails -> {
                    if (passwordEncoder.matches(loginRequestDto.getPassword(), userDetails.getPassword())) {
                        String token = jwtUtil.generateToken(userDetails.getUsername());
                        return Mono.just(token);
                    } else {
                        return Mono.error(new BadCredentialsException("Invalid credentials"));
                    }
                });
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return  accountRepository.findByUsername(username)
                .map(AccountAdapter::convertToUserDetails)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found")));
    }


}

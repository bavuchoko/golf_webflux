package com.pjs.golf_webflex.app.auth.service;

import com.pjs.golf_webflex.app.auth.adapter.AccountAdapter;
import com.pjs.golf_webflex.app.auth.dto.LoginRequestDto;
import com.pjs.golf_webflex.app.auth.repository.AccountRepository;
import com.pjs.golf_webflex.app.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthService{

  // 변경된 부분
    private final CustomUserDetailsServiceImpl customUserDetailsService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public Mono<String> authorize(LoginRequestDto loginRequestDto) {
        return customUserDetailsService.findByUsername(loginRequestDto.getUsername())
                .flatMap(userDetails -> {
                    if (passwordEncoder.matches(loginRequestDto.getPassword(), userDetails.getPassword())) {
                        return Mono.just(jwtUtil.createToken(userDetails)); // Access Token 생성
                    } else {
                        return Mono.error(new BadCredentialsException("Invalid password"));
                    }
                })
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found")));
    }



}

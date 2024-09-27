package com.pjs.golf_webflex.service;

import com.pjs.golf_webflex.dto.AccountDto;
import com.pjs.golf_webflex.dto.LoginDto;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<String> authorize(LoginDto loginRequestDto, ServerHttpResponse response);

    Mono<Boolean> validateToken(String token);

    Mono<String> reIssueToken(ServerHttpRequest request);


    Mono join(AccountDto accountRequestDto);

    Mono update(AccountDto accountRequestDto);
}

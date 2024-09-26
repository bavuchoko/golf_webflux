package com.pjs.golf_webflex.service;

import com.pjs.golf_webflex.dto.AccountRequestDto;
import com.pjs.golf_webflex.dto.LoginRequestDto;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<String> authorize(LoginRequestDto loginRequestDto, ServerHttpResponse response);

    Mono<Boolean> validateToken(String token);

    Mono<String> reIssueToken(ServerHttpRequest request);


    Mono join(AccountRequestDto accountRequestDto);

    Mono update(AccountRequestDto accountRequestDto);
}

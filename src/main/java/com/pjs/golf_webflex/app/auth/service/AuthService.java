package com.pjs.golf_webflex.app.auth.service;

import com.pjs.golf_webflex.app.auth.dto.LoginRequestDto;
import com.pjs.golf_webflex.common.TokenType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<String> authorize(LoginRequestDto loginRequestDto, ServerHttpResponse response);

    Mono<Boolean> validateToken(String token);

    Mono<String> reIssueToken(ServerHttpRequest request);

}

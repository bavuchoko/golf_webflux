package com.pjs.golf_webflex.app.auth.service;

import com.pjs.golf_webflex.app.auth.dto.LoginRequestDto;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<String> authorize(LoginRequestDto loginRequestDto);
}

package com.pjs.golf_webflex.config.filter;

import com.pjs.golf_webflex.config.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
public class JwtAuthenticationFilter extends AuthenticationWebFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(ReactiveAuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            // 비동기로 사용자 정보 가져오기
            return jwtUtil.validateToken(token)
                    .flatMap(isValid -> {
                        if (isValid) {
                            // 비동기로 사용자 정보 가져오기
                            return jwtUtil.getAuthentication(token)
                                    .flatMap(authentication -> {
                                        // SecurityContext에 인증 객체 저장
                                        SecurityContext context = new SecurityContextImpl(authentication);
                                        return chain.filter(exchange)
                                                .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(context)));
                                    });
                        } else {
                            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or missing JWT token"));
                        }
                    })
                    .onErrorResume(e -> Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or missing JWT token")));
        }

        // 토큰이 없으면 null
        return chain.filter(exchange);
    }

}
package com.pjs.golf_webflex.app.auth.service;

import com.pjs.golf_webflex.app.auth.adapter.AccountAdapter;
import com.pjs.golf_webflex.app.auth.dto.LoginRequestDto;
import com.pjs.golf_webflex.common.TokenType;
import com.pjs.golf_webflex.config.JwtUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

  // 변경된 부분
    private final CustomUserDetailsServiceImpl customUserDetailsService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public Mono<String> authorize(LoginRequestDto loginRequestDto, ServerHttpResponse response) {
        return customUserDetailsService.findByUsername(loginRequestDto.getUsername())
                .flatMap(userDetails -> {
                    if (passwordEncoder.matches(loginRequestDto.getPassword(), userDetails.getPassword())) {

                        // accessToken 생성
                        String accessToken = jwtUtil.createToken(TokenType.ACCESS_TOKEN, userDetails);
                        // 리프레시 토큰을 응답에 추가
                        addRefreshTokenToResponse(response, userDetails);
                        return Mono.just(accessToken);

                    } else {
                        return Mono.error(new BadCredentialsException("Invalid password"));
                    }
                })
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found")));
    }

    private void addRefreshTokenToResponse(ServerHttpResponse response, UserDetails userDetails) {
        String refreshToken = jwtUtil.createToken(TokenType.REFRESH_TOKEN, userDetails);
        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(1 * 24 * 60 * 60 * 15) // 15일
                .build();

        // 응답에 쿠키 추가
        response.addCookie(refreshTokenCookie);
    }

    @Override
    public Mono<Boolean> validateToken(String token) {
        return jwtUtil.validateToken(token);
    }


    /**
     * todo 토큰 갱신할 때 갱신토큰도 갱신하는게 좋아보임
     */
    @Override
    public Mono<String> reIssueToken(ServerHttpRequest request) {
        String storedRefreshToken = jwtUtil.getRefreshToken(request);

        if (StringUtils.hasText(storedRefreshToken)) {
            // 토큰이 유효한지 검증
            return jwtUtil.validateToken(storedRefreshToken)
                    .flatMap(valid -> {
                        if (valid) {
                            // 토큰이 유효하면, 인증 객체를 가져온다.
                            return jwtUtil.getAuthentication(storedRefreshToken)
                                    .flatMap(authentication -> {
                                        // 인증된 사용자 정보로 새로운 액세스 토큰 생성
                                        AccountAdapter accountAdapter = (AccountAdapter) authentication.getPrincipal();
                                        String newAccessToken = jwtUtil.createToken(TokenType.ACCESS_TOKEN, accountAdapter);
                                        return Mono.just(newAccessToken); // 새 토큰 반환
                                    });
                        } else {
                            // 토큰이 유효하지 않다면 Mono.error를 통해 예외 처리
                            return Mono.error(new IllegalArgumentException("Invalid refresh token"));
                        }
                    });
        }
        // 리프레시 토큰이 없을 경우
        return Mono.error(new IllegalArgumentException("Refresh token is missing"));
    }
}

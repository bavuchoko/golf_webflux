package com.pjs.golf_webflex.app.auth;


import com.pjs.golf_webflex.app.auth.dto.LoginRequestDto;
import com.pjs.golf_webflex.app.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@Slf4j
@RestController
@RequestMapping("/api/vi/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(
            @RequestBody LoginRequestDto loginRequestDto,
            ServerHttpResponse response
            ) {
        return authService.authorize(loginRequestDto, response)
                .map(token -> ResponseEntity.ok(token))
                .onErrorReturn(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized"));
    }

    @GetMapping("/validation")
    public Mono<ResponseEntity<String>> validation(@RequestHeader(name = "Authorization") String bearerToken) {
        if(StringUtils.hasText(bearerToken)){
            String token = bearerToken.replace("Bearer ", "");
            return authService.validateToken(token)
                    .map(valid -> {
                        if (valid) {
                            return new ResponseEntity<>(token, HttpStatus.OK); // 200
                        } else {
                            return new ResponseEntity<>("Invalid token", HttpStatus.FORBIDDEN); // 403
                        }
                    })
                    .onErrorResume(e -> Mono.just(new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN))) // 403
                    .switchIfEmpty(Mono.just(new ResponseEntity<>("Invalid token", HttpStatus.UNAUTHORIZED))); // 401
        }
        return Mono.just(new ResponseEntity<>("token is empty", HttpStatus.UNAUTHORIZED)); // 401
    }

    @GetMapping("/reissue")
    public Mono<ResponseEntity<String>> reissue(ServerHttpRequest request, ServerHttpResponse response) {
        return authService.reIssueToken(request)
                .map(accessToken -> {
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.add("Authorization", "Bearer " + accessToken);
                    return new ResponseEntity<>(accessToken, httpHeaders, HttpStatus.OK);
                })
                .onErrorReturn(new ResponseEntity<>("Failed to refresh token", HttpStatus.BAD_REQUEST));
    }

}

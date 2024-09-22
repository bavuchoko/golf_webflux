package com.pjs.golf_webflex.app.auth;


import com.pjs.golf_webflex.app.auth.dto.Account;
import com.pjs.golf_webflex.app.auth.dto.LoginRequestDto;
import com.pjs.golf_webflex.app.auth.service.AuthService;
import com.pjs.golf_webflex.config.annotation.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@RequestBody LoginRequestDto loginRequestDto) {
        return authService.authorize(loginRequestDto)
                .map(token -> ResponseEntity.ok(token))
                .onErrorReturn(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized"));
    }

    @GetMapping("/test1")
    public Mono<ResponseEntity<String>> test1(@CurrentUser Account account) {
        return Mono.just(new ResponseEntity<>(HttpStatus.OK));
    }

    @GetMapping("/test2")
    public Mono<ResponseEntity<String>> test2() {
        return Mono.just(new ResponseEntity<>(HttpStatus.OK));
    }

}

package com.pjs.golf_webflex.app.auth;


import com.pjs.golf_webflex.app.auth.dto.LoginRequestDto;
import com.pjs.golf_webflex.app.auth.service.AuthService;
import com.pjs.golf_webflex.app.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}

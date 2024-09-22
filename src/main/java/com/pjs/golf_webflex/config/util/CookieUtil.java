package com.pjs.golf_webflex.config.util;


import com.pjs.golf_webflex.common.TokenType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CookieUtil {
    public String getToken(ServerHttpRequest request, TokenType tokenType) {
        HttpCookie cookie = request.getCookies().getFirst(tokenType.getValue());
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }



}

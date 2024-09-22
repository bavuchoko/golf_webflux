package com.pjs.golf_webflex.config.util;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CookieUtil {
    public String getRefreshToken(ServerHttpRequest request) {
        HttpCookie cookie = request.getCookies().getFirst("refreshToken");
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }
}

package com.pjs.golf_webflex.config;

import com.pjs.golf_webflex.app.auth.adapter.AccountAdapter;
import com.pjs.golf_webflex.app.auth.dto.Account;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtUtil implements InitializingBean {

    private final String SECRET_KEY;
    private final long VALID_TIME;
    private final ReactiveUserDetailsService reactiveUserDetailsService;
    private static final String AUTHORITIES_KEY = "auth";
    private Key key;

    private JwtUtil(
            @Value("${spring.jwt.secret}") String secret,
            @Value("${spring.jwt.day}") long oneDay, ReactiveUserDetailsService reactiveUserDetailsService){

        this.SECRET_KEY = secret;
        this.VALID_TIME = oneDay;
        this.reactiveUserDetailsService = reactiveUserDetailsService;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(UserDetails userDetails) {
        Account account = ((AccountAdapter) userDetails).getAccount();

        String authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        Long id = account.getId();
        String userName = account.getUsername();
        String name = account.getName();
        String birth = account.getBirth().toString();
        String gender = account.getGender();

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.VALID_TIME);


        Map<String, Object> payloads = new HashMap<>();
        payloads.put("id", id);
        payloads.put("name", name);
        payloads.put("username", userName);
        payloads.put("birth", birth);
        payloads.put("gender", gender);
        payloads.put(AUTHORITIES_KEY, authorities);

        return Jwts.builder()
                .setClaims(payloads)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + VALID_TIME ))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            throw e;
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            throw e;
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            throw e;
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            throw e;
        }
    }


    public List<GrantedAuthority> extractAuthorities(String token) {
        Claims claims = extractAllClaims(token);
        String authorities = claims.get(AUTHORITIES_KEY, String.class);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
    }


    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public Mono<Authentication> getAuthentication(String token){
        String username = extractUsername(token);
        return reactiveUserDetailsService.findByUsername(username)
                .map(userDetails -> {
                    // 사용자 정보를 바탕으로 인증 객체 생성
                    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                });
    }
}
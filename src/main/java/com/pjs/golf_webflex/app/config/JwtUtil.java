package com.pjs.golf_webflex.app.config;

import com.pjs.golf_webflex.app.auth.adapter.AccountAdapter;
import com.pjs.golf_webflex.app.auth.dto.Account;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private final String SECRET_KEY;
    private final long VALID_TIME;

    private static final String AUTHORITIES_KEY = "auth";

    private JwtUtil(
            @Value("${spring.jwt.secret}") String secret,
            @Value("${spring.jwt.day}") long oneDay){

        this.SECRET_KEY = secret;
        this.VALID_TIME = oneDay;
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
                .setSubject(account.getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + VALID_TIME ))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getExpiration();
    }

    private String extractUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }
}
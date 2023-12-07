package com.ivanfrias.myapi.Security;

import com.ivanfrias.myapi.Dto.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtils {
    private final Constants constants = new Constants();

    private final String ACCESS_TOKEN_SECRET = constants.ACCESS_TOKEN_SECRET;
    private final Long ACCESS_TOKEN_VALIDITY_SECONDS = constants.ACCESS_TOKEN_VALIDITY_SECONDS;

    // Enviar data adicional
    public String createToken(long id, String email, String name, String lastname, String teamName, Double budget, boolean isEmailVerified){
        long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        Map<String, Object> extra = new HashMap<>();
        extra.put("email", email);
        extra.put("id", id);
        extra.put("name", name);
        extra.put("lastname", lastname);
        extra.put("teamName", teamName);
        extra.put("budget", budget);
        extra.put("isEmailVerified", isEmailVerified);

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDate)
                .addClaims(extra)
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                .compact();
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token){
        try{
            Claims claim = Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String email = claim.getSubject();

            return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
        } catch (JwtException e){
            return null;
        }
    }

    public TokenUtils() {
    }
}

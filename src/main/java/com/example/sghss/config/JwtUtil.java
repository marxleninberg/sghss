package com.example.sghss.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "segredo123";

    // Gera um token com base no username (email)
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1h
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Extrai username (email) do token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Verifica se o token está expirado
    public boolean isTokenValid(String token) {
        return extractExpiration(token).after(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }
}

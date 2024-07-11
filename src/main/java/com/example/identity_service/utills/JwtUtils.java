package com.example.identity_service.utills;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    @Value("${security.jwt.secret-key}")
    private String jwtSecret;

    @Value("${security.jwt.expiration-time}")
    private int jwtExpiration;
    @Value("${security.jwt.refreshExpiration}")
    private long refreshExpirationDateInMs;
    Boolean tokenExpired = false;
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationDateInMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateJwtToken(String authtoken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parse(authtoken);
            return true;

        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT Token : {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT Token is Expired : {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT :{}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT Payload is Empty: {}", e.getMessage());
        }
        return false;
    }

    public boolean isTokenExpired(String token) {
        try {
            Date expiration = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public String getUsernameFromJwtToken(String authtoken) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authtoken).getBody().getSubject();
    }
    public Date extractExpirationDateFromToken(String token) {
        final Claims claims = getClaims(token);
        return claims.getExpiration();
    }
    public Boolean isTokenNotExpired(String token) {
        final Date currentTime = new Date();
        final Date expiration = extractExpirationDateFromToken(token);
        return currentTime.before(expiration);
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token) throws Exception {
        tryGetToken(token);
        return tokenExpired;
    }

    private Claims tryGetToken(String token) throws Exception {
        try {
            Claims claims = Jwts.parser().setSigningKey(jwtSecret)
                    .parseClaimsJws(token).getBody();
            tokenExpired = true;
            return claims;
        } catch (ExpiredJwtException ex) {
            DefaultClaims claims = (DefaultClaims) ex.getClaims();
            return claims;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}

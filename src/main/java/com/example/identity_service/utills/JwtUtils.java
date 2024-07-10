package com.example.identity_service.utills;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    @Value("${security.jwt.secret-key}")
    private String jwtSecret;

    @Value("${security.jwt.expiration-time}")
    private int jwtExpiration;

    public String generateJwtToken(Authentication authentication){
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+jwtExpiration))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }
    public Key key(){
//        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);

        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
    public  boolean validateJwtToken(String authtoken){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parse(authtoken);
            return true;

        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT Token : {}",e.getMessage());
        }catch(ExpiredJwtException e){
            logger.error("JWT Token is Expired : {}",e. getMessage());
        }catch(UnsupportedJwtException e){
            logger.error("Unsupported JWT :{}", e.getMessage());
        }catch(IllegalArgumentException e){
            logger.error("JWT Payload is Empty: {}", e.getMessage());
        }
        return false;
    }

    public String getUsernameFromJwtToken(String authtoken){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authtoken).getBody().getSubject();
    }
}

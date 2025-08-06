package com.example.securitydemo.jwt;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private int jwtExpirationms;


    // Getting Jwt from header
    public String getJwtFromHeader(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        logger.debug("Authorization Header : {}", bearerToken);
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }


    // Generating token from username
    public String generateTokenFromUsername(UserDetails userDetails){
        String username = userDetails.getUsername();
        return Jwts.builder().
                subject(username).
                issuedAt(new Date()).
                expiration(new Date((new Date().getTime() + jwtExpirationms))).
                signWith(key()).
                compact();

    }


    // Getting Username from JWT token
    public String getUserNameFromJwtToke(String token){
        return Jwts.parser().
                verifyWith(key()).
                build().parseSignedClaims(token).
                getPayload().getSubject();
    }




    // Generating signing key
    // Validate JWT token
}

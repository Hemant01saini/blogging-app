package com.blogapp.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    // yeh bnata hai Header Payload Signature

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;





    private Key getSigningKey(){

        return Keys.hmacShaKeyFor(
                secretKey.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateToken(UserDetails userDetails){

        String role = userDetails
                .getAuthorities()
                .iterator()
                .next()
                .getAuthority();


        //Payload
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("role",role)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis() + jwtExpiration
                        )
                )
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token){
        return extractClaims(token,
                Claims::getSubject);
    }

    private Claims extractAllClaims(String token) {

        return Jwts
                .parser()
                .verifyWith((SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaims(String token,
                               Function<Claims,T> claimsResolver) {

        Claims claims =
                extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    public Date extractExpiration(String token) {

        return extractClaims(
                token,
                Claims::getExpiration
        );

    }

    private boolean isTokenExpired(String token) {

        return extractExpiration(token)
                .before(new Date());
    }

    public boolean isTokenValid(String token,
                                UserDetails userDetails) {

        final  String username =
                extractUsername(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }
}


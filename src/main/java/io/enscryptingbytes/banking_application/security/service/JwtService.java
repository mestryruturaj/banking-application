package io.enscryptingbytes.banking_application.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${security.jwt.secret-key:3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b}")
    private String SECRET_KEY;
    @Value("${security.jwt.expiration-time:1800000}")
    private long EXPIRATION_TIME;
    @Value("${security.jwt.issuer:BANK_APPLICATION}")
    private String ISSUER;

    //generate token
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails, EXPIRATION_TIME, ISSUER);
    }

    public String generateToken(Map<String, Object> claims, UserDetails userDetails, long jwtExpiration, String issuer) {
        Date issuedTime = new Date(System.currentTimeMillis());
        Date expirationTime = new Date(System.currentTimeMillis() + jwtExpiration);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuer(issuer)
                .setIssuedAt(issuedTime)
                .setExpiration(expirationTime)
                .compact();
    }


    //verify token
    public boolean isTokenValid(String token, UserDetails userDetails) {
        return StringUtils.equals(userDetails.getUsername(), extractUsername(token))
                && !isTokenExpired(token);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpirationTime(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        Date expirationTime = extractExpirationTime(token);
        return expirationTime.before(new Date(System.currentTimeMillis()));
    }

    private Key getSigningKey(String signingKey) {
        byte[] secretKeyBytes = Decoders.BASE64.decode(signingKey);
        return Keys.hmacShaKeyFor(secretKeyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        Claims allClaims = extractAllClaims(token);
        return claimResolver.apply(allClaims);
    }
}

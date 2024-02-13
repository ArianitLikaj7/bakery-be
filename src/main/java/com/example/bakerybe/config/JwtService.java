package com.example.bakerybe.config;

import com.example.bakerybe.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
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

    private enum TokenType {TOKEN, REFRESH};

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.refresh.token.expiration}")
    private Long refreshTokenExpiration;

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(User userDetails){
        return generateToken(getExtraClaims(userDetails), userDetails, TokenType.TOKEN);
    }

    public String generateRefreshToken(User userDetails){
        return generateToken(getExtraClaims(userDetails), userDetails, TokenType.REFRESH);
    }

    public String generateToken(Map<String, Object> extraClaims,
                                UserDetails userDetails,
                                TokenType tokenType){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(generateExpirationDate(tokenType))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Date generateExpirationDate(TokenType tokenType) {
        if (tokenType.equals(TokenType.REFRESH)) {
            return new Date(System.currentTimeMillis() + refreshTokenExpiration * 1000);
        }
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    private static Map<String, Object> getExtraClaims(User userDetails) {
        Map<String, Object> extraClaim = new HashMap<>();
        extraClaim.put("role", userDetails.getRole());
        return extraClaim;
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

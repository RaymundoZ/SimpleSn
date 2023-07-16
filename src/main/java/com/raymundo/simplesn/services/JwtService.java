package com.raymundo.simplesn.services;

import com.raymundo.simplesn.exceptions.InvalidTokenException;
import com.raymundo.simplesn.exceptions.TokenExpiredException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private static final String SECRET_KEY = "E6C83B282AEB2E022844595721CC00BBDA47CB24537C1779F9BB84F04039E1676E6BA8573E588DA1052510E3AA0A32A9E55879AE22B0C2D62136FC0A3E85F8BB";

    public String getUsername(String token) throws InvalidTokenException, TokenExpiredException {
        return getAllClaims(token).getSubject();
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        Date date = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + 3_600_000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    private Claims getAllClaims(String token) throws InvalidTokenException, TokenExpiredException {
        Jws<Claims> claimsJws;
        try {
            claimsJws = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw new InvalidTokenException(token);
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException(token);
        }
        return claimsJws.getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}

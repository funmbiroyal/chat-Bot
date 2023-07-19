package dev.gracie.elearn.services.token;

import dev.gracie.elearn.models.AppUser;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class TokenServiceImpl implements TokenService {
    Dotenv dotenv = Dotenv.configure().load();


    private final String SECRET_KEY = dotenv.get("JWT_SECRET_KEY");


    private final long JWT_TOKEN_VALIDITY = Long.parseLong(dotenv.get("JWT_TOKEN_VALIDITY"));


    @Override
    public String generateAccessToken(AppUser user) {
        Map<String, Object> authorities = new HashMap<>();
        authorities.put("authorities", user.getAuthorities());
        return Jwts
                .builder()
                .setClaims(authorities)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String extractEmail(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    @Override
    public boolean isTokenValid(String jwt, UserDetails userDetails) {
        final String username = extractEmail(jwt);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(jwt);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}

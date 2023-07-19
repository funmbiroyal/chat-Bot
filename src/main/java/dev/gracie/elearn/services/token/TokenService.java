package dev.gracie.elearn.services.token;

import dev.gracie.elearn.models.AppUser;
import org.springframework.security.core.userdetails.UserDetails;

public interface TokenService {
    String generateAccessToken(AppUser user);

    String extractEmail(String jwt);

    boolean isTokenValid(String jwt, UserDetails userDetails);
}

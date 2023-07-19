package dev.gracie.elearn.services.auth;

import dev.gracie.elearn.data.dtos.requests.LoginRequest;
import dev.gracie.elearn.data.dtos.responses.LoginResponse;
import dev.gracie.elearn.data.repositories.AppUserRepository;
import dev.gracie.elearn.models.AppUser;
import dev.gracie.elearn.services.token.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AppUserRepository userRepository;
    private final TokenService tokenService;
private final AuthenticationManager authenticationManager;
    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }

        AppUser user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
        String token = tokenService.generateAccessToken(user);
        return new LoginResponse(token);
    }
}

package dev.gracie.elearn.services.auth;

import dev.gracie.elearn.data.dtos.requests.LoginRequest;
import dev.gracie.elearn.data.dtos.responses.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
}

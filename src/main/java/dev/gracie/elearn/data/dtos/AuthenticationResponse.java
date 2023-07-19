package dev.gracie.elearn.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data

public class AuthenticationResponse {
    private String accessToken;
    private String userId;

    public static AuthenticationResponse of(String jwtToken, String userId) {
        return new AuthenticationResponse(jwtToken, userId);
    }
}

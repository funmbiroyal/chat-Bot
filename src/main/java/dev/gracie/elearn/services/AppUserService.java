package dev.gracie.elearn.services;

import dev.gracie.elearn.data.dtos.requests.RegisterRequest;
import dev.gracie.elearn.models.AppUser;

public interface AppUserService {
    AppUser registerUser(RegisterRequest userDTO);


}

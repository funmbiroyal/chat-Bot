package dev.gracie.elearn.services;

import dev.gracie.elearn.data.dtos.requests.RegisterRequest;
import dev.gracie.elearn.data.repositories.AppUserRepository;
import dev.gracie.elearn.data.repositories.AuthorityRepository;
import dev.gracie.elearn.exceptions.EmailAlreadyUsedException;
import dev.gracie.elearn.models.AppUser;
import dev.gracie.elearn.models.AuthoritiesConstants;
import dev.gracie.elearn.models.Authority;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@AllArgsConstructor
@Service
public class AppUserImpl implements AppUserService {

    private final PasswordEncoder passwordEncoder;
    private final Logger log = LoggerFactory.getLogger(AppUserService.class);

    private final AppUserRepository appUserRepository;


    private final AuthorityRepository authorityRepository;



    public AppUser registerUser(RegisterRequest userDTO) {

        appUserRepository
                .findOneByEmailIgnoreCase(userDTO.getEmail())
                .ifPresent(existingUser -> {
                    boolean removed = removeNonActivatedUser(existingUser);
                    if (!removed) {
                        throw new EmailAlreadyUsedException();
                    }
                });
        AppUser newUser = new AppUser();
        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
//        newUser.setLogin(userDTO.getLogin().toLowerCase());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        if (userDTO.getEmail() != null) {
            newUser.setEmail(userDTO.getEmail().toLowerCase());
        }

        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(generateActivationKey());
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);
        appUserRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }


    private String generateActivationKey() {
        return UUID.randomUUID().toString();
    }

    private boolean removeNonActivatedUser(AppUser existingUser) {
        if (existingUser.isActivated()) {
            return false;
        }
        appUserRepository.delete(existingUser);
        appUserRepository.flush();
        return true;
    }
}

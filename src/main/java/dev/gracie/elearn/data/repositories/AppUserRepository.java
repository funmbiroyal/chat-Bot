package dev.gracie.elearn.data.repositories;

import dev.gracie.elearn.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findOneByEmailIgnoreCase(String email);
    Optional<AppUser> findOneByLogin(String login);
    Optional<AppUser> findOneByActivationKey(String activationKey);

    Optional<AppUser> findByEmail(String email);

    AppUser findUserById(Long id);
}

package dev.gracie.elearn.controllers;

import dev.gracie.elearn.data.dtos.requests.RegisterRequest;
import dev.gracie.elearn.exceptions.InvalidPasswordException;
import dev.gracie.elearn.models.AppUser;
import dev.gracie.elearn.services.AppUserService;
import dev.gracie.elearn.services.MailServiceImpl;
import io.micrometer.common.util.StringUtils;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/")
public class AppUserController {

    private final AppUserService appUserService;

    private final MailServiceImpl mailService;

    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@Valid @RequestBody RegisterRequest userDTO) {
        if (isPasswordLengthInvalid(userDTO.getPassword())) {
            throw new InvalidPasswordException();
        }
        AppUser appUser = appUserService.registerUser(userDTO);
        Context context = new Context();
        context.setVariable("appUser", appUser);
        try {
            mailService.sendEmail(appUser,"Welcome Email","WelcomeEmail",context);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return  ResponseEntity.status(HttpStatus.CREATED).body("Registered Successfully");
    }

    private static boolean isPasswordLengthInvalid(String password) {
        return (
                StringUtils.isEmpty(password) ||
                        password.length() < RegisterRequest.PASSWORD_MIN_LENGTH ||
                        password.length() > RegisterRequest.PASSWORD_MAX_LENGTH
        );
    }
}

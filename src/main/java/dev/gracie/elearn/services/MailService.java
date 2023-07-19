package dev.gracie.elearn.services;

import dev.gracie.elearn.models.AppUser;

public interface MailService {
    void sendActivationEmail(AppUser appUser);
    void sendCreationEmail(AppUser appUser);
    void sendEmailFromTemplate(AppUser appUser, String subject, String template);
    void sendEmail(AppUser appUser);

}

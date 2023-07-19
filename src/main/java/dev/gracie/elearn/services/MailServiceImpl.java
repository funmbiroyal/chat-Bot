package dev.gracie.elearn.services;

import dev.gracie.elearn.data.dtos.requests.SendMailRequest;
import dev.gracie.elearn.data.dtos.responses.MailResponse;
import dev.gracie.elearn.models.AppUser;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
//import org.hibernate.sql.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;

@Service
@AllArgsConstructor
public class MailServiceImpl {
    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";

    private static final String BASE_URL = "baseUrl";

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;
//
    @Async
    public void sendActivationEmail(AppUser user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "Activation Email", "email.activation.title");
    }

    @Async
    public void sendCreationEmail(AppUser appUser) {
        log.debug("Sending creation email to '{}'", appUser.getEmail());
        sendEmailFromTemplate(appUser, "Account Creation Email", "email.activation.title");
    }
    @Async
    private void sendEmailFromTemplate(AppUser appUser, String subject, String template) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(appUser.getEmail());
        message.setSubject(subject);
        message.setText(template);

        javaMailSender.send(message);
        System.out.println("Email sent successfully to " + appUser);
    }
    public void sendEmail(AppUser appUser, String subject, String template, Context context) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(appUser.getEmail());
        helper.setSubject(subject);
        helper.setFrom(new InternetAddress("noreply@e-learn.com", "e-learn"));
        String emailContent = templateEngine.process(template, context);
        helper.setText(emailContent, true);

        javaMailSender.send(message);
    }

    @Async
    public void sendPasswordResetMail(AppUser user) {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/passwordResetEmail", "email.reset.title");
    }
}

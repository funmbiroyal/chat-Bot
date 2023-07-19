package dev.gracie.elearn.config;

import dev.gracie.elearn.models.AppUser;
import dev.gracie.elearn.services.MailServiceImpl;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "dev.gracie.elearn.services")
public class AppConfig {
    @Bean
    public MailServiceImpl mailService(JavaMailSender mailSender, MessageSource messageSource,
                                       SpringTemplateEngine templateEngine) {
        return new MailServiceImpl(mailSender, messageSource, templateEngine);
    }

    @Bean
    public JavaMailSender javaMailSender() {
        Dotenv dotenv = Dotenv.configure().load();
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(dotenv.get("EMAIL_USERNAME"));
       mailSender.setPassword(dotenv.get("EMAIL_PASSWORD"));


        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.debug", "true");
        return mailSender;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}


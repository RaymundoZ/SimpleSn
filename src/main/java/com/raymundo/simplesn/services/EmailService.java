package com.raymundo.simplesn.services;

import com.raymundo.simplesn.entities.UserEntity;
import com.raymundo.simplesn.exceptions.ConfigurationException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private static final String SUBJECT = "New admin confirmation!";
    private static final String TEXT = "The '%s' user with '%s' email just created an account.\nTo confirm click the link: http%s://%s%s/auth/enable/%s\nThe link will be available for 1 hour.";

    @Value(value = "${email-service.admin-email:}")
    private String adminEmail;

    @Value(value = "${email-service.domain:localhost}")
    private String domain;

    @Value(value = "${email-service.port:}")
    private String port;

    @Value(value = "${email-service.use-https:false}")
    private boolean useHttps;

    private final JavaMailSender mailSender;
    private final JwtService jwtService;

    public void sendAdminEnableEmail(UserEntity user) {
        String token = jwtService.generateToken(user);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(adminEmail);
        simpleMailMessage.setSubject(SUBJECT);
        simpleMailMessage.setText(String.format(TEXT, user.getUsername(), user.getEmail(),
                useHttps ? "s" : "", domain, port, token));
        mailSender.send(simpleMailMessage);
    }

    @PostConstruct
    private void validate() {
        if (useHttps && domain.equals("localhost"))
            throw new ConfigurationException("email-service.domain");

        if (!port.isBlank())
            port = ":" + port;

        if (adminEmail == null || adminEmail.isBlank())
            throw new ConfigurationException("email-service.admin-email");
    }
}

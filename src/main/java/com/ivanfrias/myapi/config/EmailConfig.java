package com.ivanfrias.myapi.config;

import com.ivanfrias.myapi.Dto.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class EmailConfig {
    private final Constants constants = new Constants();
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(constants.EMAIL_HOST);
        mailSender.setPort(constants.EMAIL_PORT);

        mailSender.setUsername(constants.EMAIL_SENDER);
        mailSender.setPassword(constants.EMAIL_SENDER_APLICATION_PASS);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}


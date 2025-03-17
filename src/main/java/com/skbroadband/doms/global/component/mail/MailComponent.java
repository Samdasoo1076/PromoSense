package com.skbroadband.doms.global.component.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.component
 * @File : MailComponent
 * @Program :
 * @Date : 2022-12-14
 * @Comment :
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MailComponent implements MailSender{
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    @Value("${spring.mail.sender}")
    private String sender;

    public void sendMail(String subject, String to, String template, Consumer<Context> consumer) {
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom(sender);

            Context context = new Context();
            if(!Objects.isNull(consumer)) {
                consumer.accept(context);
            }

            String html = templateEngine.process(template, context);
            mimeMessageHelper.setText(html, true);
        } catch (MessagingException e) {
            log.error("mail send fail");
            throw new RuntimeException(e);
        }

        javaMailSender.send(mimeMailMessage);
    }

    public boolean isValidEmailAddress(String email) {
        try {
            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();
        } catch (AddressException e) {
            return false;
        }

        return true;
    }
}

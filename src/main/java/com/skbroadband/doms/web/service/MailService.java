package com.skbroadband.doms.web.service;

import com.skbroadband.doms.global.component.mail.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.function.Consumer;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.web.service
 * @File : MailService
 * @Program :
 * @Date : 2023-01-06
 * @Comment :
 */
@Service
@RequiredArgsConstructor
public class MailService {
    private final MailSender mailSender;

    @Async("executor")
    public void send(String subject, String to, String template, Consumer<Context> consumer){
        mailSender.sendMail(subject, to, template, consumer);
    }

    @Async("executor")
    public void send(String subject, String to, String template){
        mailSender.sendMail(subject, to, template, null);
    }
}

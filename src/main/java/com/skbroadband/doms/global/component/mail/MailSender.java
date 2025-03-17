package com.skbroadband.doms.global.component.mail;

import org.thymeleaf.context.Context;

import java.util.function.Consumer;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.component.mail
 * @File : MailSender
 * @Program :
 * @Date : 2023-01-06
 * @Comment :
 */
public interface MailSender {
    void sendMail(String subject, String to, String template, Consumer<Context> consumer);
}

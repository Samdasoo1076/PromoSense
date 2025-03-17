package com.skbroadband.doms.global.component.mail;

import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.context.Context;

import java.util.function.Consumer;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.component.mail
 * @File : MailMockComponent
 * @Program :
 * @Date : 2023-01-06
 * @Comment :
 */
@Slf4j
//@Component
public class MailMockComponent implements MailSender{
    @Override
    public void sendMail(String subject, String to, String template, Consumer<Context> consumer) {
        log.debug("메일이 전송되었습니다.");
    }
}

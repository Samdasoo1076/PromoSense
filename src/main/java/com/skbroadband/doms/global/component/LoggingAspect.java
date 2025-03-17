package com.skbroadband.doms.global.component;

import com.skbroadband.doms.global.annotation.Log;
import com.skbroadband.doms.global.component.security.auth.Account;
import com.skbroadband.doms.global.constant.WorkType;
import com.skbroadband.doms.web.entity.AdminLog;
import com.skbroadband.doms.web.repository.AdminLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.Instant;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.component
 * @File : LoggingAspect
 * @Program :
 * @Date : 2023-02-06
 * @Comment :
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {
    private final AdminLogRepository adminLogRepository;
    @Qualifier("executor")
    private final ThreadPoolTaskExecutor executor;

    @Around("@annotation(com.skbroadband.doms.global.annotation.Log)")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
        Object object = pjp.proceed();

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if("anonymousUser".equals(authentication.getPrincipal())) {
            return object;
        }

        String content = method.getAnnotation(Log.class).content();
        WorkType workType = method.getAnnotation(Log.class).action();
        Instant now = Instant.now();

        executor.execute(() -> {
            Long admNo = ((Account)authentication.getPrincipal()).getAdmNo();

            adminLogRepository.save(AdminLog.builder()
                    .admNo(admNo)
                    .logIp(request.getRemoteAddr())
                    .task(content)
                    .logDate(now)
                    .taskType(workType.getCode())
                    .build());
        });

        return object;
    }
}

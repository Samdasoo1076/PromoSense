package com.skbroadband.doms.global.config;

import com.skbroadband.doms.global.component.mapper.DomsObjectMapper;
import com.skbroadband.doms.global.dto.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import static com.skbroadband.doms.global.utils.CommUtils.writeResponseBodyLog;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @PackageName : com.skbroadband.doms.global.config
 * @File : WebMcvConfig
 * @Program :
 * @Date : 2022-11-23
 * @Comment :
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@ConfigurationProperties("application.api")
public class WebMcvConfig implements WebMvcConfigurer {
    @Setter
    private List<String> corsAllowedOrigins;
    private final AccessDeniedHandler accessDeniedHandler;
    private final DomsObjectMapper domsObjectMapper;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(corsAllowedOrigins.toArray(new String[0]))
                .allowedMethods("GET")
                .allowedMethods("POST")
                .maxAge(3000);
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        HandlerExceptionResolver exceptionHandlerExceptionResolver = resolvers.stream()
                .filter(x -> x instanceof ExceptionHandlerExceptionResolver).findAny().orElse(null);
        int index = resolvers.indexOf(exceptionHandlerExceptionResolver);
        resolvers.add(index, (request, response, handler, exception) -> {
            Method method = ((HandlerMethod) handler).getMethod();
            if(method.getReturnType().isAssignableFrom(ResponseEntity.class)
            || method.isAnnotationPresent(ResponseBody.class)) {
                return null;
            }

            if(exception instanceof AccessDeniedException) {
                try {
                    accessDeniedHandler.handle(request, response, (AccessDeniedException) exception);
                } catch (IOException | ServletException e) {
                    throw new RuntimeException(e);
                }
            }

            log.error("{}", exception);

            HttpStatus httpStatus;
            Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
            if(status != null) {
                httpStatus = HttpStatus.resolve(Integer.parseInt(status.toString()));
            } else {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            }

            ErrorResponse errorResponse = ErrorResponse.builder()
                    .code(httpStatus.value())
                    .status(httpStatus.getReasonPhrase())
                    .message(exception.getMessage())
                    .path(request.getRequestURI())
                    .build();
            writeResponseBodyLog(errorResponse);

            return new ModelAndView("/error");
        });

        WebMvcConfigurer.super.extendHandlerExceptionResolvers(resolvers);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter(domsObjectMapper));
    }
}

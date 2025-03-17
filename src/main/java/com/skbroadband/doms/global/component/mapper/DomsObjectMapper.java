package com.skbroadband.doms.global.component.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.skbroadband.doms.global.utils.LocalDateTimeDeserializer;
import com.skbroadband.doms.global.utils.LocalDateTimeSerializer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.component
 * @File : DomsObjectMapper
 * @Program :
 * @Date : 2023-03-13
 * @Comment :
 */
@Component
public class DomsObjectMapper  extends ObjectMapper {
    private static final long serialVersionUID = -2148669317097583174L;

    public DomsObjectMapper(){
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Number.class, new NumberToStringSerializer());

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());

        registerModules(simpleModule, javaTimeModule);

        getSerializerProvider().setNullValueSerializer(new NullSerializer());
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 없는 필드로 인한 오류 무시
    }
}

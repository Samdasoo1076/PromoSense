package com.skbroadband.doms.global.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.utils
 * @File : LocalDateTimeDeserializer
 * @Program :
 * @Date : 2023-02-28
 * @Comment :
 */
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    public static final DateTimeFormatter FORMATTER = ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException {
        return LocalDateTime.parse(p.getValueAsString(), FORMATTER);
    }
}

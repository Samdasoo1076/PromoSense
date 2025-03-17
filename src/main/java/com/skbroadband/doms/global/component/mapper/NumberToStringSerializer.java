package com.skbroadband.doms.global.component.mapper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.component
 * @File : NumberToStringSerializer
 * @Program :
 * @Date : 2023-03-13
 * @Comment :
 */
public class NumberToStringSerializer extends JsonSerializer<Number> {

    @Override
    public void serialize(Number value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if(value == null) gen.writeString("");
        else gen.writeString(value.toString());
    }
}

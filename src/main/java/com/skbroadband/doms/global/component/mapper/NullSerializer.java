package com.skbroadband.doms.global.component.mapper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.component
 * @File : NullSerializer
 * @Program :
 * @Date : 2023-03-13
 * @Comment :
 */
public class NullSerializer extends JsonSerializer<Object> {
    public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        jgen.writeString("");
    }
}

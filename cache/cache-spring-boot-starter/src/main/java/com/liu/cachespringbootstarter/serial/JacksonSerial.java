package com.liu.cachespringbootstarter.serial;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

/**
 * jackson序列化
 */
@Component
public class JacksonSerial implements Serial{
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public String serial(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T deserial(String value,Class<T> clazz) {
        return objectMapper.convertValue(value,clazz);
    }
}

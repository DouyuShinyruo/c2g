package io.github.shinyruo.c2g.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ObjectMapperService {

    private final ObjectMapper objectMapper;

    @Autowired
    public ObjectMapperService(ObjectMapper objectMapper ) {
        this.objectMapper = objectMapper;
    }
    public JsonNode readTree(String jsonString) throws IOException {
        return objectMapper.readTree(jsonString);
    }

    public <T> T readValue(String jsonString, Class<T> valueType) throws IOException {
        return objectMapper.readValue(jsonString, valueType);
    }
}

package com.energytracker.datacollector.infrastructure.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class ObjectMapperTests {

    @Test
    public void testObjectMapper() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        LocalDateTime now = LocalDateTime.now();
        String serializedDate = objectMapper.writeValueAsString(now);

        System.out.println("Serialized LocalDateTime: " + serializedDate);
    }

}

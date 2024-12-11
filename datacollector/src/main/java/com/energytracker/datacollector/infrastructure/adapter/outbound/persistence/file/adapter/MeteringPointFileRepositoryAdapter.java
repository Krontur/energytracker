package com.energytracker.datacollector.infrastructure.adapter.outbound.persistence.file.adapter;

import com.energytracker.datacollector.application.port.outbound.MeteringPointFileRepositoryPort;
import com.energytracker.datacollector.domain.model.MeteringPoint;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Slf4j
@Repository
public class MeteringPointFileRepositoryAdapter implements MeteringPointFileRepositoryPort {

    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;

    public MeteringPointFileRepositoryAdapter(ResourceLoader resourceLoader, ObjectMapper objectMapper) {
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public List<MeteringPoint> loadMeteringPointsFromFile(String filePath) {
        try {
            Resource resource = resourceLoader.getResource("file:" + filePath);
            if (!resource.exists()) {
                log.warn("File not found: {}. Please check the path or create the file.", filePath);
                throw new FileNotFoundException("File not found: " + filePath);
            }
            return objectMapper.readValue(resource.getInputStream(), new TypeReference<List<MeteringPoint>>() {});
        } catch (FileNotFoundException e) {
            log.error("File not found: {}", filePath, e);
            throw new RuntimeException("File not found: " + filePath, e);
        } catch (IOException e) {
            log.error("Error reading file: {}", filePath, e);
            throw new RuntimeException("Error reading file: " + filePath, e);
        } catch (Exception e) {
            log.error("Unexpected error while loading metering points: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error while loading metering points", e);
        }
    }

    @Override
    public void saveMeteringPointsToFile(List<MeteringPoint> meteringPoints, String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                log.warn("Creating new file: {}", filePath);
                boolean isFileCreated = file.createNewFile();
                if (!isFileCreated) {
                    log.error("Failed to create file: {}", filePath);
                    throw new IOException("Failed to create file: " + filePath);
                }
            }
            objectMapper.writeValue(file, meteringPoints);
        } catch (IOException e) {
            log.error("Error saving metering points to file: {}", filePath, e);
            throw new RuntimeException("Error saving metering points to file", e);
        } catch (Exception e) {
            log.error("Unexpected error while saving metering points: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error while saving metering points", e);
        }
    }
}

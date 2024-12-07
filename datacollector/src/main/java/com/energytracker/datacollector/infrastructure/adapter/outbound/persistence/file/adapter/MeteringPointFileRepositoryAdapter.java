package com.energytracker.datacollector.infrastructure.adapter.outbound.persistence.file.adapter;

import com.energytracker.datacollector.application.port.outbound.MeteringPointFileRepositoryPort;
import com.energytracker.datacollector.domain.model.MeteringPoint;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

@Slf4j
@Repository
public class MeteringPointFileRepositoryAdapter implements MeteringPointFileRepositoryPort {

    private final ObjectMapper objectMapper;
    private final ResourceLoader resourceLoader;

    public MeteringPointFileRepositoryAdapter(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<MeteringPoint> loadMeteringPointsFromFile(String filePath) {
        try {
            Resource resource = resourceLoader.getResource("file:" + filePath);
            if (!resource.exists()) {
                log.warn("File not found, loading default file: {}", filePath);
                throw new FileNotFoundException("File not found: " + filePath);
            }
            return objectMapper.readValue(resource.getInputStream(), new TypeReference<List<MeteringPoint>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error loading metering points from file", e);
        }
    }

    @Override
    public void saveMeteringPointsToFile(List<MeteringPoint> meteringPoints, String filePath) {
        try {
            objectMapper.writeValue(new File(filePath), meteringPoints);
        } catch (Exception e) {
            throw new RuntimeException("Error saving metering points to file", e);
        }
    }
}

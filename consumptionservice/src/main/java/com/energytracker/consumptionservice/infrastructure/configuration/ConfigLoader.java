package com.energytracker.consumptionservice.infrastructure.configuration;

import com.energytracker.consumptionservice.application.port.outbound.ConfigLoaderPort;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

@Log4j2
@Component
public class ConfigLoader implements ConfigLoaderPort {

    private final Properties properties;

    public ConfigLoader(@Value("${external.config.path}") String configFilePath) {
        properties = new Properties();

        log.info("Loading config file from: {}", configFilePath);
        if (!Files.exists(Paths.get(configFilePath))) {
            log.error("Config file not found: {}", configFilePath);
            throw new RuntimeException("Config file not found");
        }
        if (!Files.isReadable(Paths.get(configFilePath))) {
            log.error("Config file is not readable: {}", configFilePath);
            throw new RuntimeException("Config file is not readable");
        }

        try (FileInputStream fis = new FileInputStream(configFilePath)) {
            properties.load(fis);
            log.info("Config file loaded successfully from: {}", configFilePath);
        } catch (Exception e) {
            log.error("Error loading config file from: {}", configFilePath, e);
            throw new RuntimeException("Error loading config file from: " + configFilePath, e);
        }
        log.info("Config file loaded successfully");
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public int getIntProperty(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

    public boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(properties.getProperty(key));
    }

}

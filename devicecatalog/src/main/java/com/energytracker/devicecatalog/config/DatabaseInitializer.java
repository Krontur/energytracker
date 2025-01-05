package com.energytracker.devicecatalog.config;

import com.energytracker.devicecatalog.application.dto.energymeter.CreateEnergyMeterRequestDto;
import com.energytracker.devicecatalog.application.dto.energymeter.EnergyMeterResponseDto;
import com.energytracker.devicecatalog.application.dto.meteringpoint.CreateMeteringPointRequestDto;
import com.energytracker.devicecatalog.application.dto.meteringpoint.MeteringPointResponseDto;
import com.energytracker.devicecatalog.application.dto.station.ChannelResponseDto;
import com.energytracker.devicecatalog.application.dto.station.CreateStationRequestDto;
import com.energytracker.devicecatalog.application.dto.station.StationResponseDto;
import com.energytracker.devicecatalog.application.port.inbound.energymeter.CreateEnergyMeterUseCase;
import com.energytracker.devicecatalog.application.port.inbound.energymeter.GetAllEnergyMetersUseCase;
import com.energytracker.devicecatalog.application.port.inbound.meteringpoint.CreateMeteringPointUseCase;
import com.energytracker.devicecatalog.application.port.inbound.station.CreateStationUseCase;
import com.energytracker.devicecatalog.application.port.inbound.station.GetAllStationsUseCase;
import com.energytracker.devicecatalog.application.port.inbound.station.GetChannelsByStationIdUseCase;
import com.energytracker.devicecatalog.application.port.inbound.station.GetLonActiveChannelsByStationIdUseCase;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.amqp.core.QueueBuilder.LeaderLocator.random;

@Log4j2
@Component
@RequiredArgsConstructor
@DependsOn({"energyMeterService", "stationService", "meteringPointService"})
public class DatabaseInitializer implements CommandLineRunner {

    @Value("${demo.init}")
    private boolean demoInit;

    private final CreateEnergyMeterUseCase createEnergyMeterUseCase;
    private final CreateStationUseCase createStationUseCase;
    private final CreateMeteringPointUseCase createMeteringPointUseCase;
    private final GetAllEnergyMetersUseCase getAllEnergyMetersUseCase;
    private final GetChannelsByStationIdUseCase getChannelsByStationIdUseCase;
    private final GetAllStationsUseCase getAllStationsUseCase;

    private void initializeDatabase() {
        log.info("Initializing database devices");

        for (int i = 0; i < 10; i++) {
            try {
                createEnergyMeterUseCase.createEnergyMeter(new CreateEnergyMeterRequestDto(
                        UUID.randomUUID().toString(),
                        "ENERGY_METER",
                        "INSTALLED",
                        "OSNF9NSDFSDIN" + i * 100,
                        "DIGITAL",
                        400,
                        "LON",
                        40,
                        2023
                ));
            } catch (Exception e) {
                log.error("Failed to create energy meter {}: {}", i, e.getMessage());
            }
        }

        for (int i = 10; i < 20; i++) {
            try {
                String deviceStatus = i % 2 == 0 ? "IN_STOCK" : "INSTALLED";
                String stationType = i % 2 == 0 ? "U1601" : "U1604";
                createStationUseCase.createStation(new CreateStationRequestDto(
                        "serialNumber" + i,
                        "STATION",
                        deviceStatus,
                        "Station number " + i,
                        stationType,
                        "A" + i,
                        900
                ));
            } catch (Exception e) {
                log.error("Failed to create station {}: {}", i, e.getMessage());
            }
        }

        List<EnergyMeterResponseDto> energyMeters = getAllEnergyMetersUseCase.getAllEnergyMeters();
        List<StationResponseDto> stations = getAllStationsUseCase.getAllStations();
        for (int i = 20; i < 30; i++) {
            try {
                List<ChannelResponseDto> channels = getChannelsByStationIdUseCase.getChannelsByStationId(stations.get(i - 20).getStationId());
                List<ChannelResponseDto> availableChannels = new ArrayList<>();
                channels.forEach(channelResponseDto -> {
                    if (!channelResponseDto.getLonIsActive()) {
                        availableChannels.add(channelResponseDto);
                    }
                });
                if (availableChannels.isEmpty()) {
                    log.warn("No available channels for station {}", stations.get(i - 20).getStationId());
                    continue;
                }
                MeteringPointResponseDto meteringPointResponseDto = createMeteringPointUseCase.createMeteringPoint(
                        new CreateMeteringPointRequestDto(
                                "Building " + i,
                                "Connection point " + i,
                                null,
                                energyMeters.get(i - 20).getEnergyMeterId(),
                                availableChannels.get(0).getChannelId(),
                                true
                        ));
                log.info("Created metering point {}", meteringPointResponseDto);
            } catch (Exception e) {
                log.error("Failed to create metering point {}: {}", i, e.getMessage());
            }
        }
    }


    @Override
    public void run(String... args) throws Exception {
        try {
            if (demoInit) {
                initializeDatabase();
            }
        } catch (Exception e) {
            log.error("Error initializing database: {}", e.getMessage());
            throw new RuntimeException("Error initializing database", e);
        }
    }
}

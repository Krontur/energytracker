package com.energytracker.datacollector.application.service;

import com.energytracker.datacollector.application.port.inbound.ManageMeteringPointsFileUseCase;
import com.energytracker.datacollector.application.port.outbound.MeteringPointFileRepositoryPort;
import com.energytracker.datacollector.domain.model.MeteringPoint;
import com.netflix.spectator.api.Meter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.List;

@Log4j2
@Service
public class MeteringPointsPersistenceService implements ManageMeteringPointsFileUseCase {

    private final MeteringPointFileRepositoryPort meteringPointFileRepositoryPort;

    public MeteringPointsPersistenceService(MeteringPointFileRepositoryPort meteringPointFileRepositoryPort) {
        this.meteringPointFileRepositoryPort = meteringPointFileRepositoryPort;
    }


    @Override
    public List<MeteringPoint> loadMeteringPointsFromFile(String filePath) {
        if (filePath == null) {
            log.error("File path cannot be null");
            throw new IllegalArgumentException("File path cannot be null");
        }
        if (Paths.get(filePath).toFile().exists()) {
            return meteringPointFileRepositoryPort.loadMeteringPointsFromFile(filePath);
        } else {
            log.error("File does not exist");
            throw new IllegalArgumentException("File does not exist");
        }
    }

    @Override
    public void saveMeteringPointsToFile(List<MeteringPoint> meteringPoints, String filePath) {

        if (meteringPoints == null || meteringPoints.isEmpty()) {
            log.error("Metering points cannot be null or empty");
            throw new IllegalArgumentException("Metering points cannot be null or empty");
        }
        if (filePath == null) {
            log.error("File path cannot be null");
            throw new IllegalArgumentException("File path cannot be null");
        }
        meteringPointFileRepositoryPort.saveMeteringPointsToFile(meteringPoints, filePath);

    }

    @Override
    public void addMeteringPointToFile(MeteringPoint meteringPoint, String filePath) {

        List<MeteringPoint> meteringPoints = loadMeteringPointsFromFile(filePath);
        boolean isDuplicate = meteringPoints.stream().anyMatch(existing ->
                existing.getMeteringPointId().equals(meteringPoint.getMeteringPointId()));
        if (isDuplicate) {
            log.warn("MeteringPoint with ID {} already exists in the file. Skipping addition.", meteringPoint.getMeteringPointId());
            throw new IllegalArgumentException("MeteringPoint with ID " + meteringPoint.getMeteringPointId() + " already exists in the file. Skipping addition.");
        }

        meteringPoints.add(new MeteringPoint(
                meteringPoint.getMeteringPointId(),
                meteringPoint.getStationTag(),
                meteringPoint.getChannelNumber()
        ));

        saveMeteringPointsToFile(meteringPoints, filePath);
        log.info("MeteringPoint with ID {} added successfully.", meteringPoint.getMeteringPointId());
    }

}

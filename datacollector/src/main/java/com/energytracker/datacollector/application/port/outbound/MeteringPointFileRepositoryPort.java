package com.energytracker.datacollector.application.port.outbound;

import com.energytracker.datacollector.domain.model.MeteringPoint;

import java.util.List;

public interface MeteringPointFileRepositoryPort {

    List<MeteringPoint> loadMeteringPointsFromFile(String filePath);

    void saveMeteringPointsToFile(List<MeteringPoint> meteringPoints, String filePath);

}

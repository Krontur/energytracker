package com.energytracker.datacollector.application.port.inbound;

import com.energytracker.datacollector.domain.model.MeteringPoint;

import java.util.List;

public interface ManageMeteringPointsFileUseCase {

    List<MeteringPoint> loadMeteringPointsFromFile(String filePath);

    void saveMeteringPointsToFile(List<MeteringPoint> meteringPoints, String filePath);

    void addMeteringPointToFile(MeteringPoint meteringPoint, String filePath);

}

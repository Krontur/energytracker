package com.energytracker.consumptionservice.application.port.outbound;

import com.energytracker.consumptionservice.domain.model.MeteringPoint;

import java.util.List;

public interface MeteringPointRepositoryPort {

    MeteringPoint saveMeteringPoint(MeteringPoint meteringPoint);

    MeteringPoint deleteMeteringPoint(MeteringPoint meteringPoint);

    List<MeteringPoint> getAllMeteringPoints();
}
